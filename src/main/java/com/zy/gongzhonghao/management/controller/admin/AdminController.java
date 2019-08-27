package com.zy.gongzhonghao.management.controller.admin;




import com.zy.gongzhonghao.management.util.HttpUtil;
import org.apache.http.HttpResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;


/**
 * 此控制器负责后台数据管理的页面跳转
 */
@Controller
@RequestMapping("/admin")
public class AdminController extends BaseController{

    //跳转到后台管理首页
    @RequestMapping("/toAdminIndex")
    public String toAdminIndex(){
        return "pc/admin";
    }

    //安全数据管理页面跳转
    @RequestMapping("/safetyaccidentnum")
    public String safetyaccidentnum(){
        return "pc/safetyaccidentnum";
    }

    //数据添加页面的控制器
    @RequestMapping("/accidentNumAdd")
    public String accidentNumAdd(){
        return "pc/safetyaccidentnum_add";
    }

    //安全指数管理控制器
    @RequestMapping("/safetyindex")
    public String toSafetyindex(){
        return "pc/consafetyindex";
    }

    //安全指数添加页面控制器
    @RequestMapping("/conSafetyIndexAdd")
    public String toConSafetyIndexAdd(){
        return "pc/consafetyindex_add";
    }



    @RequestMapping("/testMsg")
    public void t() {
        String host = "http://yzx.market.alicloudapi.com";
        String path = "/yzx/sendSms";
        String method = "POST";
        String appcode = "016291c0c6f646c68ec9759f14876937";
        Map<String, String> headers = new HashMap<>();

        headers.put("Authorization", "APPCODE " + appcode);
        Map<String, String> querys = new HashMap<>();
        querys.put("mobile", "18332566942");
        querys.put("param", "code:123456");
        //querys.put("param", "这里填写你和商家定义的变量名称和变量值填写格式看上一行代码");
        querys.put("tpl_id", "TP1710262");
        Map<String, String> bodys = new HashMap<>();


        try {

            HttpResponse response = HttpUtil.doPost(host, path, method, headers, querys, bodys);
            System.out.println(response.toString());

            //获取response的body
            //System.out.println(EntityUtils.toString(response.getEntity()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
