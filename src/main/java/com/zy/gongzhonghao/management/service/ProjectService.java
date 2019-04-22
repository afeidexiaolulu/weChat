package com.zy.gongzhonghao.management.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.zy.gongzhonghao.management.bean.Project;

import java.util.Set;

public interface ProjectService  extends IService<Project> {
    Set<String> selectItemNo();

    //插入项目
    Integer insertProject(Project project);

    //更改项目状态
    Integer updateProjectStatus(Set<String> myItemSet);
}
