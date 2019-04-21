package com.zy.gongzhonghao.management.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zy.gongzhonghao.management.bean.User;
import com.zy.gongzhonghao.management.mapper.UserMapper;
import com.zy.gongzhonghao.management.service.UserService;
import org.springframework.stereotype.Service;

import java.util.Map;


/**
 * 用户登录的实现类
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>implements UserService{


    @Override
    public User queryUserByLogin(String loginacct) {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("loginacct", loginacct);
        User user = baseMapper.selectOne(userQueryWrapper);
        return user;
    }


}
