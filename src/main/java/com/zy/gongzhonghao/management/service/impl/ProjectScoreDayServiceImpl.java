package com.zy.gongzhonghao.management.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zy.gongzhonghao.management.bean.ProjectScoreDay;
import com.zy.gongzhonghao.management.bean.TotalSafetyData;
import com.zy.gongzhonghao.management.mapper.ProjectScoreDayMapper;
import com.zy.gongzhonghao.management.service.ProjectScoreDayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProjectScoreDayServiceImpl extends ServiceImpl<ProjectScoreDayMapper, ProjectScoreDay> implements ProjectScoreDayService {

    @Autowired
    private ProjectScoreDayMapper projectScoreDayMapper;



    //批量插入每天每个项目安全指数
    @Override
    public Integer insertBatch(List<ProjectScoreDay> projectScoreDayList) {
        return projectScoreDayMapper.insertBatch(projectScoreDayList);
    }
}
