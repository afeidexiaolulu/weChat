package com.zy.gongzhonghao.management.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zy.gongzhonghao.management.bean.User;


/**
 * 此类用来处理用户的登录查询
 */

public interface UserService extends IService<User>{

    User queryUserByLogin(String username);



}
