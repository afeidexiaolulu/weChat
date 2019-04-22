package com.zy.gongzhonghao.management.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

@Data
public class RedRanking {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String statisticsDate;

    private String itemName;

    private String itemNo;

    private boolean status;

    private Integer redRankingNum;

    private Date insertTime;
}
