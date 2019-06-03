package com.zy.gongzhonghao.management.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RedRanking {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String itemName;

    private Integer redRankingNum;

    private Date insertTime;
}
