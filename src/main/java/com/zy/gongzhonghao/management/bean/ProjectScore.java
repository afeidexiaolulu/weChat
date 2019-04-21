package com.zy.gongzhonghao.management.bean;

import lombok.Data;

import java.util.Date;

@Data
public class ProjectScore {

    private Integer id;

    private String itemName;

    private String  itemNo;

    private String statisticsDate;

    private Float score;

    private Date insertTime;
}
