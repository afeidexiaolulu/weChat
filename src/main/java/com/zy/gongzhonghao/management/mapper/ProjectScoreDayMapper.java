package com.zy.gongzhonghao.management.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zy.gongzhonghao.management.bean.ProjectScoreDay;
import com.zy.gongzhonghao.management.bean.TotalSafetyData;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public interface ProjectScoreDayMapper extends BaseMapper<ProjectScoreDay> {
    //批量插入每个项目每日安全指数
    Integer insertBatch(@Param("projectScoreDayList") List<ProjectScoreDay> projectScoreDayList);

    //求出每个项目的每周安全指数
    @Select("select avg(score) from project_score_day where item_no = #{itemNo} and statistics_date >= #{startTime} and statistics_date <= #{endTime}")
    Float getAvgScoreWeek(@Param("itemNo")String itemNo, @Param("startTime")String startTime, @Param("endTime")String endTime);

    //返回有安全分数的日期列表
    List<Date> getSelectDateList();

    //通过id查询对象
    @Select("select s1.*,(select count(0) + 1 from project_score_day s2 where s2.score > s1.score and statistics_date = #{lastDate})rank_num from project_score_day s1 where statistics_date = #{lastDate} and item_no = #{itemNo} ORDER BY rank_num asc")
    ProjectScoreDay selectProjectByItemNo(@Param("itemNo") String itemNo, @Param("lastDate") Date lastDate);
}
