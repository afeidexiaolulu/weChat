package com.zy.gongzhonghao.management.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import java.util.Date;

@Data
public class SafetyTimeTable {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private Integer safetyTime;

    private Date updateTime;
}
