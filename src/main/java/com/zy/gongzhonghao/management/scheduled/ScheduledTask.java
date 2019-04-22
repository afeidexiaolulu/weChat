package com.zy.gongzhonghao.management.scheduled;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zy.gongzhonghao.management.bean.Project;
import com.zy.gongzhonghao.management.bean.TotalSafetyData;
import com.zy.gongzhonghao.management.bean.Weather;
import com.zy.gongzhonghao.management.mapper.TotalSafetyDataMapper;
import com.zy.gongzhonghao.management.service.*;
import com.zy.gongzhonghao.management.util.DateUtils;
import com.zy.gongzhonghao.management.util.HttpClientUtils;
import com.zy.gongzhonghao.management.util.JsonUtils;
import com.zy.gongzhonghao.management.util.MD5Util;
import org.apache.velocity.runtime.directive.Foreach;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * 定时任务类实现各种定时方法
 */

@Component
public class ScheduledTask {

    private static final Logger LOGGER = LoggerFactory.getLogger(ScheduledTask.class);

    @Value("${loginname}")
    private String loginname;

    @Value("${password}")
    private String password;

    @Value("${weburl}")
    private String webUrl;

    //获取三方数据的接口
    @Value("${safetyInterface}")
    private String safetyInterface;

    @Value("${weatherurl}")
    private String weatherurl;

    @Autowired
    private SafetyTimeService safetyTimeService;

    @Autowired
    private TotalSafetyDataService totalSafetyDataService;

    @Autowired
    private TotalSafetyDataMapper totalSafetyDataMapper;

    @Autowired
    private WorkerManaRateService workerManaRateService;

    @Autowired
    private TotalWarningService totalWarningService;

    @Autowired
    private WeatherService weatherService;

    @Autowired
    private ProjectService projectService;


    //每一小时执行一次查询安全时长，并放入数据库中
    @Scheduled(cron="0 1,21,41 0,1,2,3 * * ? ")
    public void getSafetyData(){

        try {
            LOGGER.debug("开始查询安全时长");
            String safetyResult = getSafetyTimeJson();
            JSONObject safetyResultObject = JSONObject.parseObject(safetyResult);
            JSONObject body1 = safetyResultObject.getJSONObject("body");
            JSONObject jsonObject2 = body1.getJSONObject("fieldDynamics");
            //得到安全时长
            String safeHours = jsonObject2.getString("safeHours");

            //调用mapper将安全工时放入到数据库中
            safetyTimeService.insertSafetyTime(new Integer(safeHours.substring(0,safeHours.indexOf("."))));
            LOGGER.info("安全时长插入到数据库中");
        } catch (NumberFormatException e) {
            e.printStackTrace();
            LOGGER.debug("查询出现问题");
        }
    }



    //从凌晨1点开始，2点结束，每20分钟执行一次
    @Scheduled(cron="0 */1 * * * ? ")
    @Transactional
    public void totalRequData() {

//获取昨天日期
        String yesDateStr = DateUtils.getDateStr(-1,"yyyy-MM-dd");

        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("SearchBeginDate",yesDateStr);
        paramMap.put("SearchEndDate",yesDateStr);
        paramMap.put("Token","48C8B324-744C-4480-9E0B-966D8632AB05");
        String mapToJson = JsonUtils.mapToJson(paramMap);

        try {
            //通过三方接口获取数据
            String result = HttpClientUtils.doPostJson("http://www.hqajz.com/ContSafetyInterface/GetItemContSafetyRecord",mapToJson);
            LOGGER.debug("开始请求所有安全数据");
            //解析数据查看是否查询成功
            JSONObject jsonObject = JSONObject.parseObject(result);
            //查看datas是否为空，如不不为空查询成功
            JSONArray datas = jsonObject.getJSONArray("Datas");
            //如果数据不为空插入数据库中
            if(!datas.isEmpty()) {
                String s = datas.toString();
                List<TotalSafetyData> totalSafetyDataList = JSONArray.parseArray(s, TotalSafetyData.class);

/*                //C测试关闭项目
                TotalSafetyData totalSafetyData1 = totalSafetyDataList.get(0);
                System.out.println("移除的项目为："+totalSafetyData1);
                totalSafetyDataList.remove(totalSafetyData1);*/

                //数据返回来的日期
                Date statisticsDate = totalSafetyDataList.get(0).getStatisticsDate();
                //如果日期数据库没有插入
                QueryWrapper<TotalSafetyData> wrapper = new QueryWrapper<>();
                wrapper.eq("statistics_date",statisticsDate);
                List<TotalSafetyData> selectList = totalSafetyDataMapper.selectList(wrapper);
                if( selectList.size() == 0){
                    //插入数据库中
                    totalSafetyDataService.insertJsonTableBatch(totalSafetyDataList);
                    LOGGER.debug("将总安全数据插入数据库");
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    //插入工人培训率和管理到岗率
                    workerManaRateService.insertTraDuty(yesDateStr);
                    //插入各种预警数值
                    totalWarningService.insertTotalWarning(yesDateStr);

                    //获取所有的项目的item_no
                    Set<String> itemSet = new HashSet<>();

                    for (TotalSafetyData totalSafetyData : totalSafetyDataList) {
                        itemSet.add(totalSafetyData.getItemNo());
                    }
                    //复制
                    Set<String> itemSetRetain = new HashSet<>();
                    itemSetRetain.addAll(itemSet);
                    //查询字典表中所有的item
                    Set<String> myItemSet = projectService.selectItemNo();

                    //求两个交集
                    itemSetRetain.retainAll(myItemSet);
                    System.out.println("交集为："+itemSetRetain);
                    //如果交集长度为0
                    if(itemSetRetain.size() == 0){
                        for(int i=0; i<totalSafetyDataList.size(); i++){
                            TotalSafetyData totalSafetyData = totalSafetyDataList.get(i);
                            //如果itemSet里面包含接口中获取到的itemno，说明为新增，插入
                            Project project = new Project();
                            project.setItemName(totalSafetyData.getItemName());
                            project.setItemNo(totalSafetyData.getItemNo());
                            project.setStatus(true);
                            project.setInsertTime(new Date());
                            //插入
                            projectService.insertProject(project);
                        }
                        LOGGER.debug("第一次插入工程项目数据完成");
                    }else{
                        //itemSet中有，myItemSet中没有  即为新增的项目 插入到项目字典中
                        itemSet.removeAll(itemSetRetain);
                        LOGGER.debug("新增的工程项目为："+ itemSet);
                        //插入字典表中
                        for(int i=0; i< totalSafetyDataList.size(); i++){
                            TotalSafetyData totalSafetyData = totalSafetyDataList.get(i);
                            //如果itemSet里面包含接口中获取到的itemno，说明为新增，插入
                            if(itemSet.contains(totalSafetyData.getItemNo())){
                                Project project = new Project();
                                project.setItemName(totalSafetyData.getItemName());
                                project.setItemNo(totalSafetyData.getItemNo());
                                project.setStatus(true);
                                project.setInsertTime(new Date());
                                //插入
                                projectService.insertProject(project);
                                LOGGER.debug("新增的工程项目插入完成"+itemSet);
                            }
                        }
                        //itemSet中没有，myItemSet中有，即为关闭项目  更新项目状态为0
                        myItemSet.removeAll(itemSetRetain);
                        LOGGER.debug("关闭的工程项目为："+myItemSet);
                        //更新状态为关闭
                        if(myItemSet.size() != 0){
                            projectService.updateProjectStatus(myItemSet);
                            LOGGER.debug("关闭的工程项目关闭完成："+myItemSet);
                        }
                    }
                    //插入后结束定时任务
                    LOGGER.debug("各个安全 数据插入完成，定时任务结束");
                    return;
                }else {
                    LOGGER.debug("已插入过各种安全数据");
                }
            }
            //失败异常捕获
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    //每天凌晨0点01分开始获取天气,10分钟一次
    @Scheduled(cron = "0 1 0/3 * * ? ")
    public void WeatherTask(){

        try {
            //封装到weatherService中,通过接口获取天气
            Integer result = weatherService.insertWeatherMsgByInterface();
            if(result == 1){
                LOGGER.debug("插入天气数据成功");
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.debug("插入天气信息失败");
        }
    }


    //获取安全时长json数据的方法
    private String getSafetyTimeJson() {
        String s = webUrl+"/loginCustomService/appEncryption?account=";
        String safetyDataStr = HttpClientUtils.doPost(s + loginname + "&pwd=" + password);
        //利用fastjosn来获取json字符串中的某个数据
        JSONObject jsonObject = JSONObject.parseObject(safetyDataStr);
        String body = jsonObject.getString("body");
        JSONObject jsonObject1 = JSONObject.parseObject(body);
        //获取到用户名密码
        String encryptAccount = jsonObject1.getString("encryptAccount");
        String encryptPwd = jsonObject1.getString("encryptPwd");
        //将用户名密码拼接后进行app登录
        String tokenStr = HttpClientUtils.doPost(webUrl+"/loginCustomService/appLogin?account="+encryptAccount+"&pwd="+encryptPwd);
        //解析返回数据
        JSONObject tokenObject = JSONObject.parseObject(tokenStr);
        String tokenBody = tokenObject.getString("body");
        JSONObject jsonObjectToken = JSONObject.parseObject(tokenBody);
        //获取到token字符串
        String token = jsonObjectToken.getString("token");
        //对token进行加密
        String digestToken = MD5Util.digest(token);
        //携带token进行接口调用获取安全时长
        return HttpClientUtils.doGet(webUrl+"/webapi/homePageService/fieldDynamics?token=" + digestToken);
    }

}


