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

    @Autowired
    private SafetyIndexService safetyIndexService;

    //每一小时执行一次查询安全时长，并放入数据库中
    @Scheduled(cron="0 0 0/1 * * ?")
    //@Scheduled(cron="0 */1 * * * ?")
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



    //从凌晨0点开始，3点结束，每20分钟执行一次
    @Scheduled(cron="0 */1 * * * ? ")
    //@Scheduled(cron="0 0 0/2 * * ?")
    @Transactional
    public void totalRequData() {

        //获取昨天日期
        String yesDateStr = DateUtils.getDateStr(-9,"yyyy-MM-dd");
        //获取请求参数
        String jsonString = DateUtils.getJsonString(-9);

        try {
            //通过三方接口获取数据
            String result = HttpClientUtils.doPostJson(safetyInterface,jsonString);
            LOGGER.info("开始请求所有安全数据");
            //解析数据查看是否查询成功
            JSONObject jsonObject = JSONObject.parseObject(result);
            //查看datas是否为空，如不不为空查询成功
            JSONArray datas = jsonObject.getJSONArray("Datas");
            //if(datas)


            //如果数据不为空插入数据库中
            if(!datas.isEmpty()) {
                String s = datas.toString();
                List<TotalSafetyData> totalSafetyDataList = JSONArray.parseArray(s, TotalSafetyData.class);

                //数据返回来的日期
                Date statisticsDate = totalSafetyDataList.get(0).getStatisticsDate();
                //如果日期数据库没有插入
                QueryWrapper<TotalSafetyData> wrapper = new QueryWrapper<>();
                wrapper.eq("statistics_date",statisticsDate);
                List<TotalSafetyData> selectList = totalSafetyDataMapper.selectList(wrapper);
                if( selectList.size() == 0){
                    //插入数据库中
                    Integer insertNum = totalSafetyDataService.insertJsonTableBatch(totalSafetyDataList);
                    if(insertNum != totalSafetyDataList.size()){
                        LOGGER.error("总安全数据时失败");
                        throw new Exception();
                    }
                    LOGGER.info("将总安全数据插入数据库");

                    //插入工人培训率和管理到岗率
                    Integer insertTraDuty = workerManaRateService.insertTraDuty(yesDateStr);
                    if(insertTraDuty != 1){
                        LOGGER.error("插入工人培训率失败");
                        throw new Exception();
                    }
                    //插入各种预警数值
                    Integer insertTotalWarning = totalWarningService.insertTotalWarning(yesDateStr);
                    if(insertTotalWarning != 1){
                        LOGGER.error("插入各种预警值失败");
                        throw new Exception();
                    }

                    //插入区域项目的安全指数
                    Integer insertSafetyIndex = safetyIndexService.insertSafetyIndexByInterface(totalSafetyDataList, -9);
                    if(insertSafetyIndex != 1){
                        LOGGER.error("插入区域安全指数失败");
                        throw new Exception();
                    }

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
                    LOGGER.debug("交集为："+itemSetRetain);
                    //itemSet中有，myItemSet中没有  即为新增的项目 插入到项目字典中
                    itemSet.removeAll(itemSetRetain);
                    //itemSet中没有，myItemSet中有，即为关闭项目  更新项目状态为0
                    myItemSet.removeAll(itemSetRetain);

                    List<Project> projectList = new ArrayList<>();
                    //插入新增工程
                    if(itemSet.size()!= 0){
                        for(int i=0; i< totalSafetyDataList.size(); i++){
                            TotalSafetyData totalSafetyData = totalSafetyDataList.get(i);
                            //如果itemSet里面包含接口中获取到的itemno，说明为新增，插入
                            if(itemSet.contains(totalSafetyData.getItemNo())){
                                Project project = new Project(null,totalSafetyData.getItemName(),totalSafetyData.getItemNo(),true,new Date());
                                projectList.add(project);
                            }
                        }
                        //插入
                        Integer insertProjectBatch = projectService.insertProjectBatch(projectList);
                        if(insertProjectBatch != itemSet.size()){
                            LOGGER.error("批量插入工程失败");
                            throw new Exception();
                        }
                        LOGGER.debug("新增的工程项目插入完成"+itemSet);
                    }
                    //更新状态为关闭
                    if(myItemSet.size() != 0){
                        Integer updateProjectStatus = projectService.updateProjectStatus(myItemSet);
                        if(myItemSet.size() != updateProjectStatus){
                            LOGGER.error("更新状态失败");
                            throw new Exception();
                        }
                        LOGGER.info("关闭的工程项目关闭完成："+myItemSet);
                    }
                    //插入后结束定时任务
                    LOGGER.info("各个安全 数据插入完成，定时任务结束");
                    return;
                }else {
                    LOGGER.info("已插入过各种安全数据");
                }
            }
            //失败异常捕获
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    //每天凌晨0点01分开始获取天气,10分钟一次
    //@Scheduled(cron = "0 1 0/3 * * ? ")
    @Scheduled(cron="0 */1 * * * ? ")
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


