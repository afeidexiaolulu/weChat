package com.zy.gongzhonghao.management.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zy.gongzhonghao.management.bean.ProjectRate;
import com.zy.gongzhonghao.management.bean.TotalSafetyData;

import java.util.List;


public interface ProjectRateService extends IService<ProjectRate> {


    //查询所有的管理指数
    List<ProjectRate> selectAll();
}
