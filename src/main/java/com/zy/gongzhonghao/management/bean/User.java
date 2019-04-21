package com.zy.gongzhonghao.management.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import java.util.Date;

/**
 * 此类为登录用户的实体类
 */

//@data 注解自动提供get，set，toString，equals，haseCode注解
@Data
public class User {

    //主键ID，自增
    @TableId(type = IdType.AUTO)
    private Integer id;

    private String loginacct;

    private String userpwd;

    private String username;

    private Date createTime;

}
