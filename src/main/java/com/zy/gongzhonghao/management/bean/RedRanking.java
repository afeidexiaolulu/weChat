package com.zy.gongzhonghao.management.bean;

import lombok.Data;

import java.util.Date;

@Data
public class RedRanking {

    private Integer id;

    private String statisticsDate;

    private String itemName;

    private String itemNo;

    private boolean status;

    private Integer redRankingNum;

    private Date insertTime;
}
