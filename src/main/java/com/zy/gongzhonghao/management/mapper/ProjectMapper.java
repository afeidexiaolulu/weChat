package com.zy.gongzhonghao.management.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zy.gongzhonghao.management.bean.Project;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ProjectMapper extends BaseMapper<Project> {
    //批量插入
    Integer insertProjectBacth(@Param("projectList") List<Project> projectList);
}
