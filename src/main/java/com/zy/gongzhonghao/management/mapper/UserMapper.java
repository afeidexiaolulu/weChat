package com.zy.gongzhonghao.management.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zy.gongzhonghao.management.bean.User;
import org.springframework.stereotype.Component;


/**
 * 接口为查询用户的接口
 */
@Component
public interface UserMapper extends BaseMapper<User> {

}
