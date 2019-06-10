package com.zy.gongzhonghao.management.controller.phonecontroller;


import com.zy.gongzhonghao.management.bean.ProjectScoreDay;
import com.zy.gongzhonghao.management.bean.SafetyIndex;
import com.zy.gongzhonghao.management.bean.TotalWarning;
import com.zy.gongzhonghao.management.bean.Weather;
import com.zy.gongzhonghao.management.controller.admin.BaseController;
import com.zy.gongzhonghao.management.controller.model.phone.*;
import com.zy.gongzhonghao.management.mapper.*;
import com.zy.gongzhonghao.management.service.*;
import com.zy.gongzhonghao.management.util.MyPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 手机端的控制器
 */
@Controller
@RequestMapping("/phone")
public class PhoneController extends BaseController {


    @Autowired
    private SafetyIndexMapper safetyIndexMapper;

    @Autowired
    private SafetyIndexService safetyIndexService;

    @Autowired
    private WorkerManaRateService workerManaRateService;

    @Autowired
    private SafetyDataService safetyDataService;

    @Autowired
    private TotalWarningService totalWarningService;

    @Autowired
    private WeatherService weatherService;

    @Autowired
    private ProjectScoreWeekService projectScoreWeekService;

    @Autowired
    private ProjectScoreDayService projectScoreDayService;


    //去往手机公共号主页跳转
    @RequestMapping("/toIndex")
    public String toIndex( ){
        return "phone/phoneindex";
    }

    //去往安全行为页面跳转
    @RequestMapping("/toSafetyBehaviour")
    public String toSafetyBehaviour(){
        return "phone/phonesafetybehaviour";
    }

    //去往安全数据页面跳转
    @RequestMapping("/toSafetyData")
    public String toSafetyData(){
        return "phone/phonesafetydata";
    }


    //红黑榜页面跳转
    @RequestMapping("/toRankingTable")
    public String toRankingTable(){
        return "phone/phoneranktable";
    }

    //施工安全指数页面跳转
    @RequestMapping("/toSafetyIndex")
    public String toSafetyIndex(){
        return "phone/phonesafetyindex";
    }

    //安全状态监控
    @RequestMapping("/toSafetyStatus")
    public String toSafetyStatus(){
        return "phone/phonesafetystatussata";
    }

    //安全页面跳转
    @RequestMapping("/toSafetyStatusLine")
    public String toSafetySatusLine(){
        return "phone/phonesafetystatusline";
    }


    //首页区域安全数据返回接口
    @RequestMapping("/phoneSafetyIndex")
    @ResponseBody
    public WeaIndexDto safetyIndexData(){
        WeaIndexDto weaIndexDto = new WeaIndexDto();
        SafetyIndex safetyIndex = safetyIndexMapper.selectLastOne();
        if(safetyIndex != null){
            weaIndexDto.setSafetyIndex(safetyIndex.getSafetyNum());
        }else {
            weaIndexDto.setSafetyIndex(new Float(80.00));
        }
        Weather weatherMsg = weatherService.getWeatherMsg();
        if(weatherMsg != null){
            String weatherStr = ""+weatherMsg.getYmd()+" "+weatherMsg.getWeek()+" "+weatherMsg.getType()+" "+weatherMsg.getNotice();
            weaIndexDto.setWeatherStr(weatherStr);
        }else {
            weaIndexDto.setWeatherStr("");
        }
        return weaIndexDto;
    }


    //安全数据页面数据请求接口
    @RequestMapping("/phoneSafetyData")
    @ResponseBody
    public SafetyNumDto getPhoneSafetyData(){
        star();
        //调用方法查询安全数据对象
        SafetyNumDto safetyNumDto = safetyDataService.getPhoneSafetyData();
        //返回安全数据对象给前台
        return safetyNumDto;
    }

    //安全行为监控页面数据请求接口
    @RequestMapping("/safetyBehaviourMonData")
    @ResponseBody
    public Object safetyBehaviourMonData(){
        //调用service进行数据查询
        SafetyBehaviourDto safetyBehaviourDto = workerManaRateService.selectSafetyBehaviourDto();
        //返回结果
        return safetyBehaviourDto;
    }


    //安全指数页面数据回显
    @RequestMapping("/phoneSafetyIndexData")
    @ResponseBody
    public SafetyIndexDto getPhoneSafetyIndex(){
        //返回结果集
        return safetyIndexService.selectLast10SaIndex();
    }

    //安全状态页面数据回显
    @RequestMapping("/phoneSafetyStatus")
    @ResponseBody
    public TotalWarning getPhoneSafetyStatus(){
        return totalWarningService.selectLastOne();
    }

    //安全状态折线图返回数据
    @RequestMapping("/phoneSafetyStatusLine")
    @ResponseBody
    public SafetyStatusLineDto getPhoneSafetyStatusLine(){
        return totalWarningService.getPhoneSafetyStatusLine();
    }

    //红黑榜返回数据折线图
    @RequestMapping("/phoneRankTable")
    @ResponseBody
    public RankingTableDto getPhoneRankTable(){
        //返回红黑榜数据
        return projectScoreWeekService.getPhoneRankTable();
    }


    //总排名页面跳转
    @RequestMapping("toTotalRank")
    public String toTotalRank(){
        return "phone/phonetotalrank";
    }

    //返回手机端项目排名查询的日期条件
    @RequestMapping("/phoneSelectDate")
    @ResponseBody
    public List<String> getSelectDate(){
        //返回查询的日期条件
        return projectScoreDayService.getSelectDateList();
    }


    //返回手机端查询最新日期的所有项目分数
    @RequestMapping("queryPageFirst")
    @ResponseBody
    public MyPage<ProjectScoreDay> projectRankList(Integer pageSize, Integer pageNo){
        //将参数传到map中
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("pageSize",pageSize);
        paramMap.put("pageNo",pageNo);
        //进行分页查询，没有筛选条件
        MyPage<ProjectScoreDay> page =projectScoreDayService.queryPage(paramMap);
        //返回分页对象
        return page;
    }


    //带有条件的查询
    @RequestMapping("queryPageByCondition")
    @ResponseBody
    public MyPage<ProjectScoreDay> queryPageByCondition(Integer pageSize, Integer pageNo, String queryDate, String queryWord){
        //将查询参数封装为参数map
        Map<Object, Object> paramMap = new HashMap<>();
        paramMap.put("pageSize",pageSize);
        paramMap.put("pageNo",pageNo);
        paramMap.put("queryDate",queryDate);
        paramMap.put("queryWord",queryWord);
        //调用 service层
        return projectScoreDayService.queryPageByCondition(paramMap);
    }

    //项目分数详情页跳转
    @RequestMapping("details")
    public String toProjectDetail(){
        return "phone/details";
    }

    //项目分数详情请求接口， 通过itemNo和日期查询项目分数详情
    @RequestMapping("toProjectDetail")
    @ResponseBody
    public ProjectScoreDay toProjectDetail(String itemNo,String queryDate){
        //根据id查询项目每日分数对象
        ProjectScoreDay projectScoreDay = projectScoreDayService.selectProjectByItemNoAndDate(itemNo,queryDate);
        return projectScoreDay;
    }
}
