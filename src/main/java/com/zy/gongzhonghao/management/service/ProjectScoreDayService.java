package com.zy.gongzhonghao.management.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zy.gongzhonghao.management.bean.ProjectScoreDay;
import com.zy.gongzhonghao.management.bean.TotalSafetyData;
import com.zy.gongzhonghao.management.util.MyPage;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface ProjectScoreDayService extends IService<ProjectScoreDay> {

    //求出每个项目的前5名和后5名
    Integer insertBatch(List<ProjectScoreDay> projectScoreDayList);

    //插入每个项目每周平均分
    Integer insertRankingTable(List<TotalSafetyData> totalSafetyDataList,Integer diff);

    //删除所有数据
    Integer deleteAll();

    //返回有每日项目分数的日期列表
    List<String> getSelectDateList();

    //不加查询条件的最新日期分页数据
    MyPage<ProjectScoreDay> queryPage(Map<String, Object> paramMap);

    //根据条件查询项目每日安全数据分页
    MyPage<ProjectScoreDay> queryPageByCondition(Map<Object, Object> paramMap);

    //查询昨天每个项目的分数并排序
    List<ProjectScoreDay> selectProjectListByDate(Date statisticsDate);

    //批量更新字段
    Integer updateRankNumBatch(List<ProjectScoreDay> projectScoreDays);

    //通过itemNo和date查询项目详情
    ProjectScoreDay selectProjectByItemNoAndDate(String itemNo, String statisticsDate);
}
