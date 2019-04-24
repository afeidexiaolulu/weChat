package com.zy.gongzhonghao.management;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zy.gongzhonghao.management.bean.*;
import com.zy.gongzhonghao.management.controller.model.phone.SafetyBehaviourDto;
import com.zy.gongzhonghao.management.controller.model.phone.SafetyIndexDto;
import com.zy.gongzhonghao.management.controller.model.phone.SafetyNumDto;
import com.zy.gongzhonghao.management.controller.model.phone.SafetyStatusLineDto;
import com.zy.gongzhonghao.management.mapper.*;
import com.zy.gongzhonghao.management.service.*;
import com.zy.gongzhonghao.management.util.DateUtils;
import com.zy.gongzhonghao.management.util.HttpClientUtils;
import com.zy.gongzhonghao.management.util.JsonUtils;
import com.zy.gongzhonghao.management.util.MD5Util;
import org.apache.commons.lang.ArrayUtils;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.test.context.junit4.SpringRunner;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


//@Ignore
@RunWith(SpringRunner.class)
@SpringBootTest
public class ManagementApplicationTests {

    private static final Logger LOGGER = LoggerFactory.getLogger(ManagementApplicationTests.class);

    @Value("${loginname}")
    private String loginname;

    @Value("${password}")
    private String password;

    @Value("${weatherurl}")
    private String weatherurl;


    @Value("${Mmin}")
    private Integer Mmin;

    @Value("${Mmax}")
    private Integer Mmax;

    @Value("${a1}")
    private Float al;

    @Value("${a2}")
    private Float a2;

    @Value("${a3}")
    private Float a3;



    @Autowired
    private SafetyTimeMapper safetyTimeMapper;

    @Autowired
    private AccidentNumMapper accidentNumMapper;

    @Autowired
    private SaTiMaxRecordMapper maxRecordMapper;

    @Autowired
    private SafetyIndexMapper safetyIndexMapper;

    @Autowired
    private WorkerManaRateMapper workerManaRateMapper;

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

    @Test
    public void contextLoads() {

        String zhongyeadmin = MD5Util.digest("zhongyeadmin");
        System.out.println(zhongyeadmin);
    }

    //测试获取安全时长
    @Test
    public void getSatefyData(){
        String s = "http://120.77.254.210:8888/ibps-platform-webapi/api/loginCustomService/appEncryption?account=";
        String safetyDataStr = HttpClientUtils.doPost(s + loginname + "&pwd=" + password);
        //利用fastjosn来获取json字符串中的某个数据
        JSONObject jsonObject = JSONObject.parseObject(safetyDataStr);
        String body = jsonObject.getString("body");
        JSONObject jsonObject1 = JSONObject.parseObject(body);
        //获取到用户名密码
        String encryptAccount = jsonObject1.getString("encryptAccount");
        String encryptPwd = jsonObject1.getString("encryptPwd");
        //将用户名密码拼接后进行app登录
        String tokenStr = HttpClientUtils.doPost("http://120.77.254.210:8888/ibps-platform-webapi/api/loginCustomService/appLogin?account="+encryptAccount+"&pwd="+encryptPwd);
        //解析返回数据
        JSONObject tokenObject = JSONObject.parseObject(tokenStr);
        String tokenBody = tokenObject.getString("body");
        JSONObject jsonObjectToken = JSONObject.parseObject(tokenBody);
        //获取到token字符串
        String token = jsonObjectToken.getString("token");
        //对token进行32位小写加密
        String digestToken = MD5Util.digest(token);
        System.out.println("token为"+digestToken);
        //携带token进行接口调用获取安全时长
        String s1 = HttpClientUtils.doGet("http://120.77.254.210:8888/ibps-platform-webapi/api/webapi/homePageService/fieldDynamics?token=" + digestToken);
        System.out.println("获取的json为" + s1);
        JSONObject safetyResultObject = JSONObject.parseObject(s1);
        JSONObject body1 = safetyResultObject.getJSONObject("body");
        JSONObject jsonObject2 = body1.getJSONObject("fieldDynamics");
        String safeHours = jsonObject2.getString("safeHours");
        System.out.println("safeHours"+safeHours);
    }


    @Test
    public void test1(){

        Integer integer = safetyTimeMapper.selectSafetyTime();
        System.out.println("查询出来的数据为："+integer);
        List<AccidentNumTable> accidentNumTables =accidentNumMapper.seletcDataAndDateLast3Month();
        System.out.println("数据为"+accidentNumTables);

    }

    //安全数据测试
    @Test
    public void test2(){
        SafetyNumDto safetyNumDto = new SafetyNumDto();
        //查询最高安全时长，赋值
        SafetyTimeMax saTiMaxRecord = maxRecordMapper.selectById("1");

        System.out.println(saTiMaxRecord.getMaxRecord());
        safetyNumDto.setSafetyTimeBig(saTiMaxRecord.getMaxRecord().toString());
        //查询实时安全工时，赋值
        Integer safetyTime = safetyTimeMapper.selectSafetyTime();
        safetyNumDto.setSafetyTime(safetyTime.toString());
        //查询近三天的安全事故和日期
        List<AccidentNumTable> accidentNumTables = accidentNumMapper.seletcDataAndDateLast3Month();
        System.out.println("数据为："+accidentNumTables);
        int intTem[] = new int[3];
        Date dateTem[] = new Date[3];
        for (int i=0; i< accidentNumTables.size();i++){
            intTem[i] = accidentNumTables.get(i).getAccidentNum();
            dateTem[i] = accidentNumTables.get(i).getAccidentDate();
        }
        safetyNumDto.setAccidentNum(intTem);

        String stringArr[] = new String[3];
        //将日期转换为字符串
        SimpleDateFormat sdf = new SimpleDateFormat("yy年M月");
        for(int i =0;i<intTem.length;i++){
            stringArr[i] = sdf.format(dateTem[i]);
        }
        safetyNumDto.setDate(stringArr);
        //将数据返回给前台
        System.out.println(safetyNumDto);
    }

    //查询最新安全指数
    @Test
    public void testSafetyIndexLastOne(){
        SafetyIndex safetyIndex = safetyIndexMapper.selectLastOne();
        System.out.println(safetyIndex);
    }

    //工人培训率和管理人员到岗率mapper测试
    @Test
    public void testSafetyBehaviourLast10Day(){
        List<WorkerManaRate> workerManaRatelist = workerManaRateMapper.selectLast10day();

        System.out.println(workerManaRatelist.size());
        System.out.println(workerManaRatelist);
    }

    //安全行为监督返回结果测试
    @Test
    public void testSafetyBahaviourLast10DayDate(){
        //对象封装
        SafetyBehaviourDto safetyBehaviourDto = new SafetyBehaviourDto();

        //查询最近10天的工人培训率和管理人员到岗率
        List<WorkerManaRate> workerManaRatelist = workerManaRateMapper.selectLast10day();
        //工人培训率10天
        int workTrain[] = new int[10];
        //管理人员到岗率10天
        int manaDuty[] = new int[10];
        //显示的日期
        String dateString[] = new String[10];

        SimpleDateFormat sdf = new SimpleDateFormat("M/d");
        for (int i=0; i< workerManaRatelist.size();i++){
            manaDuty[i] = workerManaRatelist.get(i).getManaDutyRate();
            workTrain[i] = workerManaRatelist.get(i).getWorkerTrainRate();
            dateString[i] = sdf.format(workerManaRatelist.get(i).getStatisticsDate());
        }
        safetyBehaviourDto.setManaDutyRate(workerManaRatelist.get(1).getManaDutyRate());
        safetyBehaviourDto.setWorkerTrainRate(workerManaRatelist.get(1).getWorkerTrainRate());
        //翻转
        ArrayUtils.reverse(workTrain);
        ArrayUtils.reverse(manaDuty);
        ArrayUtils.reverse(dateString);
        //赋值
        safetyBehaviourDto.setWorkTrain(workTrain);
        safetyBehaviourDto.setManaDuty(manaDuty);
        safetyBehaviourDto.setDateString(dateString);
        System.out.println(safetyBehaviourDto);
    }

    @Test
    public void testArray(){
        int arry[] = {1,2,3,4};
        for(int i=0; i<arry.length; i++){
            System.out.println(arry[i]);
        }
        ArrayUtils.reverse(arry);

        for(int i=0; i<arry.length; i++){
            System.out.println(arry[i]);
        }
    }


    //横琴施工接口测试
    @Test
    public void shiGongInterfaceTest(){

        Calendar cal   =   Calendar.getInstance();
        cal.add(Calendar.DATE,   -1);
        String yesterday = new SimpleDateFormat( "yyyy-MM-dd ").format(cal.getTime());
        System.out.println(yesterday);
        String result =  HttpClientUtils.doPostJson("http://www.hqajz.com/ContSafetyInterface/GetItemContSafetyRecord","{\n" +
                "    \"SearchBeginDate\": \"2019-04-15\",\n" +
                "    \"SearchEndDate\": \"2019-04-15\",\n" +
                "    \"Token\": \"48C8B324-744C-4480-9E0B-966D8632AB05\"\n" +
                "}");
        System.out.println(result);

        //解析数据存入数据库中
        JSONObject jsonObject = JSONObject.parseObject(result);
        String stateCode = jsonObject.getString("StateCode");
        System.out.println(stateCode);

        JSONArray datas = jsonObject.getJSONArray("Datas");
        if(!datas.isEmpty()) {
            String s = datas.toString();
            List<TotalSafetyData> totalSafetyDataList = JSONArray.parseArray(s, TotalSafetyData.class);
            System.out.println(totalSafetyDataList);
            System.out.println(totalSafetyDataList.size());
            //插入数据库中 动态sql
            Integer integer = totalSafetyDataService.insertJsonTableBatch(totalSafetyDataList);
            //返回
            System.out.println(integer);
        }
    }


    //定时任务，计算每日工人培训率和管理人员到岗率，存入数据库
    @Test
    public void testWorkerAndManaRate(){
        Calendar cal   =   Calendar.getInstance();
        cal.add(Calendar.DATE,   -1);
        String yesterday = new SimpleDateFormat( "yyyy-MM-dd ").format(cal.getTime());
        //输出昨天日期
        System.out.println(yesterday);
    }

    //测试工人培训率和管理在岗率
    @Test
    public void testNull(){
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);

        String yesterday = new SimpleDateFormat("yyyy-MM-dd ").format(cal.getTime());

        WorkerManaRate workerManaRate = new WorkerManaRate();

        //取出昨天的所有在职工人数
        Integer yesWorkerNum = totalSafetyDataService.getYesWorkerNum("2019-04-13");
        //取出昨天所有培训工人数
        Integer yesWorkerTraNum = totalSafetyDataService.getYesWorkerTraNum("2019-04-13");

        //数据校验
        if((yesWorkerNum != null && yesWorkerNum != 0 )&& yesWorkerTraNum != null){
            //计算工人培训率
            float wokerTraRate = (float)yesWorkerTraNum / yesWorkerNum;
            wokerTraRate = wokerTraRate * 100;
            //将数值格式化
            DecimalFormat df = new DecimalFormat("0");
            String s = df.format(wokerTraRate);
            Integer workerTraRate =  new Integer(s);
            workerManaRate.setWorkerTrainRate(workerTraRate);
        }else {
            workerManaRate.setWorkerTrainRate(0);
        }



        //取出昨天所有的在职管理人员数量
        Integer yesManaNum = totalSafetyDataService.getYesManaNum(yesterday);

        //取出昨天所有考勤管理人员数量
        Integer yesManaDutyNum = totalSafetyDataService.getYesManaDutyNum(yesterday);
        if((yesManaNum != null && yesManaNum != 0)&& yesManaDutyNum != null){
            float manaDutyRate = (float) yesManaDutyNum / yesManaNum;
            manaDutyRate = manaDutyRate * 100;
            //将数值格式化
            DecimalFormat df = new DecimalFormat("0");
            String s = df.format(manaDutyRate);
            Integer workerTraRate =  new Integer(s);
            workerManaRate.setManaDutyRate(workerTraRate);
        }else {
            workerManaRate.setManaDutyRate(0);
        }


        //将字符串日期解析为时间类
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Date statisticsDate = null;
        try {
            statisticsDate = sdf.parse(yesterday);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        workerManaRate.setStatisticsDate(statisticsDate);

        //插入数据库
        Integer result = workerManaRateMapper.insert(workerManaRate);

        System.out.println(result);
    }


    @Test
    public void scheduled(){
        //获取昨天日期
        Calendar cal   =   Calendar.getInstance();
        cal.add(Calendar.DATE,   -1);
        String yesterday = new SimpleDateFormat( "yyyy-MM-dd ").format(cal.getTime());
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("SearchBeginDate",yesterday);
        paramMap.put("SearchEndDate",yesterday);
        paramMap.put("Token","48C8B324-744C-4480-9E0B-966D8632AB05");
        String mapToJson = JsonUtils.mapToJson(paramMap);

        //捕获异常
        try {
            //通过三方接口获取数据
            String result = HttpClientUtils.doPostJson("http://www.hqajz.com/ContSafetyInterface/GetItemContSafetyRecord",mapToJson);

            LOGGER.info(result);
            //解析数据查看是否查询成功
            JSONObject jsonObject = JSONObject.parseObject(result);
            //查看datas是否为空，如不不为空查询成功
            JSONArray datas = jsonObject.getJSONArray("Datas");

            //如果数据不为空插入数据库中
            if(!datas.isEmpty()) {
                String s = datas.toString();
                List<TotalSafetyData> totalSafetyDataList = JSONArray.parseArray(s, TotalSafetyData.class);
                //数据返回来的日期
                Date statisticsDate = totalSafetyDataList.get(1).getStatisticsDate();

                //如果日期数据库没有插入
                QueryWrapper<TotalSafetyData> wrapper = new QueryWrapper<>();
                wrapper.eq("statistics_date",statisticsDate);
                List<TotalSafetyData> selectList = totalSafetyDataMapper.selectList(wrapper);
                LOGGER.debug("查询数据库中是否有数据");
                if( selectList.size() == 0){
                    //插入数据库中
                    totalSafetyDataService.insertJsonTableBatch(totalSafetyDataList);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    //插入工人培训率和管理到岗率
                    workerManaRateService.insertTraDuty(yesterday);

                    //插入各种预警数值
                    totalWarningService.insertTotalWarning(yesterday);
                    LOGGER.debug("插入成功直接返回");
                    //插入后返回结束任务
                    return;
                }
            }
            LOGGER.debug("插入不成功，继续请求");
            //如果失败，异常捕获
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.debug("三方接口调取失败");
        }
    }



    //国家气象局天气预报接口测试
    @Test
    public void weatherInterface(){
        try{
            String s = HttpClientUtils.doGet(weatherurl);//可使用
            //解析为json对象
            JSONObject jsonObject = JSONObject.parseObject(s);
            JSONArray data = jsonObject.getJSONObject("data").getJSONArray("forecast");

            JSONObject jsonObject1 = data.getJSONObject(0);
            Weather weather = JSONObject.parseObject(jsonObject1.toString(), Weather.class);
            //插入前先获取，如果获取为0个，则插入
            System.out.println(weather);
        }catch (Exception e){
            e.printStackTrace();
            LOGGER.debug("获取天气失败");
        }

    }


    //测试string类自带的substring方法,截取范围[start,end)
    @Test
    public void testString(){
        String num = "1234567";
        System.out.println(num.length());
        String subNum = num.substring(0,num.length()-3);
        System.out.println(subNum);
    }

    @Test
    public void test11(){
        Integer num = 30259611;
        int length = num.toString().length();
        System.out.println(length);
    }


    @Test
    public void safetyIndexTest(){
        SafetyIndexDto safetyIndexDto = new SafetyIndexDto();

        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd");
        //选出最近10天安全指数
        List<SafetyIndex> safetyIndexList = safetyIndexMapper.selectLast10SaIndex();
        //查询出来的结果长度
        int size = safetyIndexList.size();
        //创建同等长度数组
        Float safetyIndex[] = new Float[size];
        String safetyDate[] = new String[size];
        for(int i=0;i<size;i++){
            safetyIndex[i] = safetyIndexList.get(i).getSafetyNum();
            safetyDate[i]=sdf.format(safetyIndexList.get(i).getSafetyDate());
        }
        //给最新安全指数负责
        safetyIndexDto.setSafetyIndex(safetyIndex[0]);
        org.apache.commons.lang3.ArrayUtils.reverse(safetyIndex);
        org.apache.commons.lang3.ArrayUtils.reverse(safetyDate);
        //安全指数数组
        safetyIndexDto.setSafetyIndexArr(safetyIndex);
        safetyIndexDto.setSafetyIndexDateArr(safetyDate);

        LOGGER.debug("安全 指数dto"+safetyIndexDto.toString());
    }


    @Test
    public void testSafetyStatus(){
        SafetyStatusLineDto safetyStatusLineDto = new SafetyStatusLineDto();

        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd");
        List<TotalWarning> totalWarningList = totalWarningService.selectLast10TotalWarningList();

        int size = totalWarningList.size();

        String dateString[] = new String[size];
        Integer craneWeight[] = new Integer[size];
        Integer lifterWeight[] = new Integer[size];
        Integer noiseWarning[] = new Integer[size];
        Integer dustWarning[] = new Integer[size];
        Integer carWarning[] = new Integer[size];

        if(totalWarningList != null && totalWarningList.size() != 0){
            for(int i = 0; i < size; i++){
                carWarning[i]=totalWarningList.get(size-i-1).getCarWarning();
                craneWeight[i]=totalWarningList.get(size-i-1).getCraneWeight();
                dustWarning[i]=totalWarningList.get(size-i-1).getDustWarning();
                noiseWarning[i]=totalWarningList.get(size-i-1).getNoiseWarning();
                lifterWeight[i]=totalWarningList.get(size-i-1).getLifterWeight();
                dateString[i]=sdf.format(totalWarningList.get(size-i-1).getStatisticsDate());
            }
            safetyStatusLineDto.setCarWarning(carWarning);
            safetyStatusLineDto.setCraneWeight(craneWeight);
            safetyStatusLineDto.setDateString(dateString);
            safetyStatusLineDto.setDustWarning(dustWarning);
            safetyStatusLineDto.setLifterWeight(lifterWeight);
            //赋值
            LOGGER.debug("赋值输出+"+safetyStatusLineDto);
        }
        LOGGER.debug("未赋值输出+"+safetyStatusLineDto);
    }



    @Test
    public void testSet(){
        Set<String> set1 = new HashSet<>();
        Set<String> set2 = new HashSet<>();

        set1.add("a");
        set1.add("b");
        set1.add("c");
        set2.add("a");
        set2.add("d");
        //交集
        set1.retainAll(set2);

        System.out.println("交集集为是 "+set1);

        set1.removeAll(set2);

        System.out.println("差集集为是 "+set1);
    }


    @Test
    public void testInsertProject(){

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
/*            if(datas.isEmpty()){
                //工人培训，管理到岗，各种预警，安全数据
                //插入工人培训率和管理到岗率
                workerManaRateService.insertTraDuty(yesDateStr);
                //插入各种预警数值
                totalWarningService.insertTotalWarning(yesDateStr);
                //计算每个项目的今天的安全指数
                safetyIndexService.insertSafetyIndexByInterface(totalSafetyDataList,-1);


            }*/
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
                    totalSafetyDataService.insertJsonTableBatch(totalSafetyDataList);
                    LOGGER.debug("将总安全数据插入数据库");
                    //插入工人培训率和管理到岗率
                    workerManaRateService.insertTraDuty(yesDateStr);
                    //插入各种预警数值
                    totalWarningService.insertTotalWarning(yesDateStr);
                    //计算每个项目的今天的安全指数
                    safetyIndexService.insertSafetyIndexByInterface(totalSafetyDataList,-1);


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
                        projectService.insertProjectBatch(projectList);
                        LOGGER.debug("新增的工程项目插入完成"+itemSet);
                    }
                    //更新状态为关闭
                    if(myItemSet.size() != 0){
                        projectService.updateProjectStatus(myItemSet);
                        LOGGER.debug("关闭的工程项目关闭完成："+myItemSet);
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
    @Test
    public void WeatherTask(){
        //封装到weatherService中,通过接口获取天气
        Integer result = weatherService.insertWeatherMsgByInterface();
        if(result == 1){
            LOGGER.debug("插入天气数据成功");
            return;
        }
        if(result == 2){
            LOGGER.debug("已插入过天气数据");
        }
    }


    @Test
    public void testSafetyIndexByInterface(){
        //获取昨天日期
        String yesDateStr = DateUtils.getDateStr(-1,"yyyy-MM-dd");

        String jsonString = DateUtils.getJsonString(-1);

        try {
            //通过三方接口获取数据
            String result = HttpClientUtils.doPostJson("http://www.hqajz.com/ContSafetyInterface/GetItemContSafetyRecord",jsonString);
            LOGGER.debug("开始请求所有安全数据");
            //解析数据查看是否查询成功
            JSONObject jsonObject = JSONObject.parseObject(result);
            //查看datas是否为空，如不不为空查询成功
            JSONArray datas = jsonObject.getJSONArray("Datas");
            //如果数据不为空插入数据库中
            if(!datas.isEmpty()) {
                String s = datas.toString();
                List<TotalSafetyData> totalSafetyDataList = JSONArray.parseArray(s, TotalSafetyData.class);

                if(true){

                    //插入工人培训率和管理到岗率
                    workerManaRateService.insertTraDuty(yesDateStr);
                    //计算每个项目的今天的安全指数
                    safetyIndexService.insertSafetyIndexByInterface(totalSafetyDataList,-1);

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
                        LOGGER.debug("关闭的工程项目关闭完成："+myItemSet);
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

}


