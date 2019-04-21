package com.zy.gongzhonghao.management.controller.phonecontroller;


import com.zy.gongzhonghao.management.bean.SafetyIndex;
import com.zy.gongzhonghao.management.bean.TotalWarning;
import com.zy.gongzhonghao.management.bean.Weather;
import com.zy.gongzhonghao.management.controller.admin.BaseController;
import com.zy.gongzhonghao.management.controller.model.phone.*;
import com.zy.gongzhonghao.management.mapper.*;
import com.zy.gongzhonghao.management.service.*;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


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
}
