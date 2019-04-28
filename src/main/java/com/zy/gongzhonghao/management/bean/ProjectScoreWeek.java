package com.zy.gongzhonghao.management.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

@Data
public class ProjectScoreWeek {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String itemName;

    private String  itemNo;

    private String statisticsDate;

    private Float score;

    private Date insertTime;
}
