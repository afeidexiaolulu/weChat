package com.zy.gongzhonghao.management.controller.model.phone;

import lombok.Data;

//安全状态折线图dto

@Data
public class SafetyStatusLineDto {

    private String dateString[];

    private Float craneWeight[];

    private Float lifterWeight[];

    private Float noiseWarning[];

    private Float dustWarning[];

    private Float carWarning[];
}
