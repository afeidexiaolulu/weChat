package com.zy.gongzhonghao.management.controller.model.phone;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class SafetyNumDto {

    //安全工时
    private String safetyTime;

    //最高安全记录
    private String safetyTimeBig;

    //事故时间,事故数量
    private int[]  accidentNum;

    private String[] date;

}
