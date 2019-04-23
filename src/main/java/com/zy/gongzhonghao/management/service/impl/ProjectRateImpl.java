package com.zy.gongzhonghao.management.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zy.gongzhonghao.management.bean.ProjectRate;
import com.zy.gongzhonghao.management.bean.TotalSafetyData;
import com.zy.gongzhonghao.management.mapper.ProjectRateMapper;
import com.zy.gongzhonghao.management.service.ProjectRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;





@Service
public class ProjectRateImpl extends ServiceImpl<ProjectRateMapper, ProjectRate> implements ProjectRateService {

    //查询所有的管理指数
    @Override
    public List<ProjectRate> selectAll() {
        return baseMapper.selectList(null);
    }
}
