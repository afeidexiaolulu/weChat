package com.zy.gongzhonghao.management.controller.model.phone;

import lombok.Data;

//安全状态折线图dto

@Data
public class SafetyStatusLineDto {

    private String dateString[];

    private Integer craneWeight[];

    private Integer lifterWeight[];

    private Integer noiseWarning[];

    private Integer dustWarning[];

    private Integer carWarning[];
}
