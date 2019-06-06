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

    //通过itemNo和日期查询项目
    //@Select("select s1.*,(select count(0) + 1 from project_score_day s2 where case when s2.score > s1.score then s2.score > s1.score when s2.score = s1.score then s2.id > s1.id end and statistics_date = #{lastDate})rank_num from project_score_day s1 where statistics_date = #{lastDate} and item_no = #{itemNo} ORDER BY rank_num desc")
    //@Select("select * from (SELECT u.*, @rank := @rank + 1 as rank_num FROM (SELECT * FROM project_score_day where statistics_date = #{lastDate} ORDER BY score DESC) u , (SELECT @rank := 0) r) t where t.item_no = #{itemNo}")
    @Select("select * from project_score_day where statistics_date = #{date} and item_no = #{itemNo}")
    ProjectScoreDay selectProjectByItemNoAndDate(@Param("itemNo") String itemNo, @Param("date") String date);

    //通过昨天日期查询每个项目并排名
    @Select("select * from (SELECT u.*, @rank := @rank + 1 as rank_num FROM (SELECT * FROM project_score_day where statistics_date = #{statisticsDate} ORDER BY score DESC) u , (SELECT @rank := 0) r) t")
    List<ProjectScoreDay> selectProjectListByDate(Date statisticsDate);

    //批量更新项目每天排名
    Integer updateRankNumBatch(@Param("projectScoreDays") List<ProjectScoreDay> projectScoreDays);
}


