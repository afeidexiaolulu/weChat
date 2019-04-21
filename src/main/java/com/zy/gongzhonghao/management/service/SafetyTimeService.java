package com.zy.gongzhonghao.management.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zy.gongzhonghao.management.bean.SafetyTimeTable;

/**
 * 此接口为插入安全工时的接口
 */
public interface SafetyTimeService extends IService<SafetyTimeTable> {

    //向安全工时数据库中插入数据
    Integer insertSafetyTime(Integer safetyTime);
}
