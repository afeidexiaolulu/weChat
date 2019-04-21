package com.zy.gongzhonghao.management.interceptor;


import com.zy.gongzhonghao.management.bean.User;
import com.zy.gongzhonghao.management.util.Const;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.Set;


//定义为组件，添加到ioc容器中

@Component
//拦截器可以继承自HandlerInterceptorAdapter或者实现
public class UserInterceptor extends HandlerInterceptorAdapter {
    //实现里面的preHandle方法，在进入controller方法前进行拦截
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {

        //从request中获取uri，将rui与白名单进行比较，这些白名单不用登录即可访问，
        //获取rui
        String requestURI = request.getRequestURI();
        //创建白名单
        Set<String> baimingdan = new HashSet<>();
        //将可以正常访问的地址加入白名单
        baimingdan.add("/login");
        baimingdan.add("/doLogin");
        //判断rui是否在白名单中  如果在 返回true
        if (baimingdan.contains(requestURI)){
            return true;
        }else {
            //从request中获取session 从session中获取用户
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute(Const.LOGIN_MEMBER);
            //如果获取不到user就证明没有登录
            if(user == null){
                //重定向到登录页面
                response.sendRedirect(request.getContextPath()+"/login");
                return false;
            }else {
                return true;
            }
        }
    }
}
