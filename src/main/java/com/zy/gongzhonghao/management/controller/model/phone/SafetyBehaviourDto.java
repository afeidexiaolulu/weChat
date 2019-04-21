package com.zy.gongzhonghao.management.controller.model.phone;

import lombok.Data;

@Data
public class SafetyBehaviourDto {

    private Integer workerTrainRate;

    private Integer manaDutyRate;

    //工人培训率10天
    private int workTrain[] = new int[10];

    //管理人员到岗率10天
    private int manaDuty[] = new int[10];

    //显示的日期
    private String dateString[] = new String[10];

}
