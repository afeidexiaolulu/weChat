package com.zy.gongzhonghao.management.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zy.gongzhonghao.management.bean.SafetyIndex;
import com.zy.gongzhonghao.management.bean.TotalSafetyData;
import com.zy.gongzhonghao.management.controller.model.phone.SafetyIndexDto;

import java.util.List;
import java.util.Map;

public interface SafetyIndexService extends IService<SafetyIndex> {


    Page<SafetyIndex> queryPage(Map<String, Object> paramMap);


    Integer insertSafetyIndex(SafetyIndex safetyIndex1);

    Integer deleteById(Integer id);

    //按照日期 选择最近 10天的安全指数
    SafetyIndexDto selectLast10SaIndex();

    //通过接口自动获取安全指数
    Integer insertSafetyIndexByInterface(List<TotalSafetyData> totalSafetyDataList, Integer diff);

    /*Integer insertSafetyIndexByInterface1(List<TotalSafetyData> totalSafetyDataList);*/
}
