package com.zy.gongzhonghao.management.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zy.gongzhonghao.management.bean.Project;
import com.zy.gongzhonghao.management.mapper.ProjectMapper;
import com.zy.gongzhonghao.management.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ProjectServiceImpl extends ServiceImpl<ProjectMapper, Project> implements ProjectService{

    @Autowired
    private ProjectMapper projectMapper;

    @Override
    public Set<String> selectItemNo() {

        HashSet<String> projectSet = new HashSet<>();
        QueryWrapper<Project> wrapper = new QueryWrapper<>();
        wrapper.eq("status",1);
        List<Project> projects = baseMapper.selectList(wrapper);
        if(projects.size() != 0){
            for (Project project : projects) {
                projectSet.add(project.getItemNo());
            }
            return projectSet;
        }else {
            return projectSet;
        }
    }

    //批量插入项目
    @Override
    public Integer insertProjectBatch(List<Project> projectList) {
        return projectMapper.insertProjectBacth(projectList);
    }


    /**
     * 更新项目状态
     * @param myItemSet
     * @return
     */
    @Override
    public Integer updateProjectStatus(Set<String> myItemSet) {
        Integer result = 0;
        for (String item_no : myItemSet) {
            //创建对象
            Project project = new Project();
            project.setStatus(false);
            UpdateWrapper<Project> updateWrapper = new UpdateWrapper<>();
            updateWrapper.ge("item_no",item_no);
            for(int i=0; i<= myItemSet.size(); i++){
                result += baseMapper.update(project,updateWrapper);
            }
        }
        return result;
    }

    //插入项目
    @Override
    public void insertProject(Project project) {
        baseMapper.insert(project);
    }
}
