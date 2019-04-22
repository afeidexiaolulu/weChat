package com.zy.gongzhonghao.management.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.zy.gongzhonghao.management.bean.Project;

import java.util.List;
import java.util.Set;

public interface ProjectService  extends IService<Project> {
    Set<String> selectItemNo();

    //插入项目
    Integer insertProjectBatch(List<Project> projectList);

    //更改项目状态
    Integer updateProjectStatus(Set<String> myItemSet);

    //插入项目
    void insertProject(Project project);
}
