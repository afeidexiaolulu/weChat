package com.zy.gongzhonghao.management.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zy.gongzhonghao.management.bean.ProjectScoreWeek;
import com.zy.gongzhonghao.management.controller.model.phone.RankingTableDto;

import java.util.Date;
import java.util.List;

public interface ProjectScoreWeekService extends IService<ProjectScoreWeek> {

    //选出黑榜前5名select5BlackTable()
    List<ProjectScoreWeek> select5BlackTable();

    //选出红榜前5名
    List<ProjectScoreWeek> select5RedTable();

    //获取红黑榜的dto
    RankingTableDto getPhoneRankTable();

    //更新红榜次数
    Integer updateRedTable(String itemName);

    Integer updateBlackTable(String itemName);

    Float getLastweekAvgScore();

    //获取红榜得分前五的记录
    List<ProjectScoreWeek> selectScoreRedTable();

    //获取黑榜得分前五的记录
    List<ProjectScoreWeek> selectScoreBlackTable();
}
