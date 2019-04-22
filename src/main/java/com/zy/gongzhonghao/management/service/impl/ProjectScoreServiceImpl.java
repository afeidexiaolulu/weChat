package com.zy.gongzhonghao.management.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zy.gongzhonghao.management.bean.ProjectScore;
import com.zy.gongzhonghao.management.mapper.ProjectScoreMapper;
import com.zy.gongzhonghao.management.service.ProjectScoreService;
import org.springframework.stereotype.Service;

@Service
public class ProjectScoreServiceImpl extends ServiceImpl<ProjectScoreMapper, ProjectScore> implements ProjectScoreService {
}
