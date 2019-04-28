package com.zy.gongzhonghao.management.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zy.gongzhonghao.management.bean.ProjectScoreDay;
import com.zy.gongzhonghao.management.bean.TotalSafetyData;

import java.util.List;

public interface ProjectScoreDayService extends IService<ProjectScoreDay> {


    Integer insertBatch(List<ProjectScoreDay> projectScoreDayList);
}
