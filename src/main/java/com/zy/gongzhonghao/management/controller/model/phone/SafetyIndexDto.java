package com.zy.gongzhonghao.management.controller.model.phone;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


//安全指数页面返回的dto
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SafetyIndexDto {

    private Float safetyIndex;

    private Float[] safetyIndexArr;

    private String[] safetyIndexDateArr;

}
