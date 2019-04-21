package com.zy.gongzhonghao.management.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.zy.gongzhonghao.management.bean.TotalWarning;
import com.zy.gongzhonghao.management.controller.model.phone.SafetyStatusLineDto;

import java.util.List;


public interface TotalWarningService extends IService<TotalWarning> {

    //插入最新的预警指数
    Integer insertTotalWarning(String yesterday);

    //查询最近10天的预警指数
    List<TotalWarning> selectLast10TotalWarningList();

    //最新预警指数
    TotalWarning selectLastOne();

    //预警指数折线图
    SafetyStatusLineDto getPhoneSafetyStatusLine();
}
