package com.zy.gongzhonghao.management.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zy.gongzhonghao.management.bean.ProjectScoreDay;
import com.zy.gongzhonghao.management.bean.TotalSafetyData;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public interface ProjectScoreDayMapper extends BaseMapper<ProjectScoreDay> {
    //批量插入每个项目每日安全指数
    Integer insertBatch(@Param("projectScoreDayList") List<ProjectScoreDay> projectScoreDayList);

    //求出每个项目的每周安全指数
    Float getAvgScoreWeek(String itemNo);

    //返回有安全分数的日期列表
    List<Date> getSelectDateList();

}
