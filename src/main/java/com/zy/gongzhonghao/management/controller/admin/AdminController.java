package com.zy.gongzhonghao.management.controller.admin;




import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


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



}
