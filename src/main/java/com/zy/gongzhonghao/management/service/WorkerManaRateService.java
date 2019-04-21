package com.zy.gongzhonghao.management.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zy.gongzhonghao.management.bean.WorkerManaRate;
import com.zy.gongzhonghao.management.controller.model.phone.SafetyBehaviourDto;

public interface WorkerManaRateService extends IService<WorkerManaRate> {

    //查询近10天培训率和到岗率以及最新培训和到岗率
    SafetyBehaviourDto selectSafetyBehaviourDto();

    //计算昨天到岗率和培训率，插入数据库中
    Integer insertTraDuty(String yesterday);

}
