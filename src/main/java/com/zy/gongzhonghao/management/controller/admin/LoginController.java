package com.zy.gongzhonghao.management.controller.admin;

import com.zy.gongzhonghao.management.bean.User;
import com.zy.gongzhonghao.management.service.UserService;
import com.zy.gongzhonghao.management.util.Const;
import com.zy.gongzhonghao.management.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * 用来管理登录的登录的controller
 */

@Controller
public class LoginController extends BaseController {

    //自动注入UserService
    @Autowired
    private UserService userService;

    //登录页面
    @GetMapping("/login")
    public String toLogin(){
        //去往登录页面
        return "pc/login";
    }

    //登录页发生的ajax请求

    @PostMapping("/doLogin")
    @ResponseBody       //单一架构，所有用户信息放到session中即可
    public Object doLogin(String loginacct, String password, HttpSession session){
        //创建result的map
        star();
       try {
            User user = userService.queryUserByLogin(loginacct);
            //如果为空，则用户名错误
            if(user == null) {
                message(Const.LOGIN_LOGINACCT_ERROR);
                success(false);
                return end();
            }
            //如果密码不相同就是密码错误
            if(!user.getUserpwd().equals(MD5Util.digest(password))) {
                System.out.println("结果不相等才进入此");
                System.out.println(user.getUserpwd());
                System.out.println(MD5Util.digest(password));
                message(Const.LOGIN_USERPSWD_ERROR);
                success(false);
                return end();
            }
            //如果查出来用户 将用户放到session中进行共享
            session.setAttribute(Const.LOGIN_MEMBER, user);
            success(true);
        } catch (Exception e) {
            success(false);
            message("系统异常，请联系管理员");
            e.printStackTrace();
        }
        return end();
    }

    //用户退出功能
    @RequestMapping("/loginout")
    public String loginOut(HttpSession session){
        //如果session不为空
        if(session != null){
            //移除session中的用户数据
            session.removeAttribute(Const.LOGIN_MEMBER);
            session.invalidate();
        }
        return "redirect:/login";
    }


}
