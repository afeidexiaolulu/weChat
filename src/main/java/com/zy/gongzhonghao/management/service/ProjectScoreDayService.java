package com.zy.gongzhonghao.management.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zy.gongzhonghao.management.bean.ProjectScoreDay;
import com.zy.gongzhonghao.management.bean.TotalSafetyData;
import org.apache.ibatis.annotations.Param;

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
    Page<ProjectScoreDay> queryPage(Map<String, Object> paramMap);

    //根据条件查询项目每日安全数据分页
    Page<ProjectScoreDay> queryPageByCondition(Map<Object, Object> paramMap);

    //通过itemNo进行查询
    ProjectScoreDay selectProjectByItemNo(String itemNo);
}
