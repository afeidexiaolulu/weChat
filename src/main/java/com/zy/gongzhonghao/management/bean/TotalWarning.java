package com.zy.gongzhonghao.management.bean;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

@Data
public class TotalWarning {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private Date statisticsDate;

    private Float craneWeight;

    private Float lifterWeight;

    private Float noiseWarning;

    private Float dustWarning;

    private Float carWarning;

    private Date insert_time;

}
