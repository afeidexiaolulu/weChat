package com.zy.gongzhonghao.management.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zy.gongzhonghao.management.bean.ProjectScoreDay;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ProjectScoreDayMapper extends BaseMapper<ProjectScoreDay> {
    Integer insertBatch(@Param("projectScoreDayList") List<ProjectScoreDay> projectScoreDayList);
}
