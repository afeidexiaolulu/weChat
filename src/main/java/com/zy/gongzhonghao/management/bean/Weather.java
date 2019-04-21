package com.zy.gongzhonghao.management.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

@Data
public class Weather {

    //主键自增
    @TableId(type = IdType.AUTO)
    private Integer id;

    private String ymd;

    private String type;

    private String notice;

    private String week;

    private Date insertTime;

}
