package com.zy.gongzhonghao.management.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectScoreWeek {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String itemName;

    private String  itemNo;

    private Date statisticsDate;

    private Float score;

    private Integer redTableNum;

    private Integer blackTableNum;

    private Date insertTime;
}
