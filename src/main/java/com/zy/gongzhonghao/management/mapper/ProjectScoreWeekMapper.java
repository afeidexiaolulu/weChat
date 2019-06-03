package com.zy.gongzhonghao.management.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zy.gongzhonghao.management.bean.ProjectScoreWeek;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;


@Component
public interface ProjectScoreWeekMapper extends BaseMapper<ProjectScoreWeek> {
    //插入每周的项目平均得分
    Integer insertBatchWeek(@Param("weekList") List<ProjectScoreWeek> weekList);

    //选出最新红榜前5名
    List<ProjectScoreWeek> select5RedTable();

    //选出最新黑板前5名
    List<ProjectScoreWeek> select5BlackTable();

    //获取上周的平均分
    Float getLastweekAvgScore();

    //获取分数为前五的所有记录
    List<ProjectScoreWeek> selectScoreRedTable();

    //获取分数为后五的所有记录
   List<ProjectScoreWeek> selectScoreBlackTable();
}
