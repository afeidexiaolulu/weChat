package com.zy.gongzhonghao.management.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zy.gongzhonghao.management.bean.BlackRanking;
import com.zy.gongzhonghao.management.bean.ProjectScoreWeek;
import com.zy.gongzhonghao.management.bean.RedRanking;
import com.zy.gongzhonghao.management.controller.model.phone.RankingTableDto;
import com.zy.gongzhonghao.management.mapper.BlackRankingMapper;
import com.zy.gongzhonghao.management.mapper.ProjectScoreWeekMapper;
import com.zy.gongzhonghao.management.mapper.RedRankingMapper;
import com.zy.gongzhonghao.management.service.ProjectScoreWeekService;
import com.zy.gongzhonghao.management.util.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;

@Service
public class ProjectScoreWeekServiceImpl extends ServiceImpl<ProjectScoreWeekMapper, ProjectScoreWeek> implements ProjectScoreWeekService {

    @Autowired
    private ProjectScoreWeekMapper projectScoreWeekMapper;

    @Autowired
    private RedRankingMapper redRankingMapper;

    @Autowired
    private BlackRankingMapper blackRankingMapper;

    @Override
    public List<ProjectScoreWeek> select5BlackTable() {
        return projectScoreWeekMapper.select5BlackTable();
    }

    @Override
    public List<ProjectScoreWeek> select5RedTable() {
        return projectScoreWeekMapper.select5RedTable();
    }


    //获取红黑榜的Dto
    @Override
    public RankingTableDto getPhoneRankTable() {
        RankingTableDto rankingTableDto = new RankingTableDto();

        //查询红榜前5名
        List<ProjectScoreWeek> redTables = select5RedTable();
        if(redTables != null && redTables.size()>0){
            for(int i = 0;i < redTables.size();i++){
                ProjectScoreWeek week = redTables.get(i);
                if(week.getScore()== null ){
                    week.setScore(60.0f);
                }
            }
            //redTables.removeIf(a->a.getScore()==null);
        }
        //赋值
        rankingTableDto.setRedTable(redTables);
        
        //查询黑榜前5名
        List<ProjectScoreWeek> blackTables = select5BlackTable();
        if(blackTables != null && blackTables.size()>0){
            for(int i = 0;i < blackTables.size();i++){
                ProjectScoreWeek week = blackTables.get(i);
                if(week.getScore()== null ){
                    week.setScore(60.0f);
                }
            }
            //blackTables.removeIf(a->a.getScore()==null);
        }
        //赋值
        rankingTableDto.setBlackTable(blackTables);
        
        //查询红榜次数最多的前5名
        List<RedRanking> redRankings = redRankingMapper.selectRed5SumMax();
        //赋值
        rankingTableDto.setRedRankingList(redRankings);

        //查询黑榜次数最多的前5名
        List<BlackRanking> blackRankings = blackRankingMapper.selectBlack5SumMax();
        //赋值
        rankingTableDto.setBlackRankingList(blackRankings);

        Float avgScore = getLastweekAvgScore();

        if(avgScore == null){
            rankingTableDto.setSafetyScore("60.0");
        }else {
            float result = (float) (Math.round(avgScore * 10)) / 10;
            rankingTableDto.setSafetyScore(new DecimalFormat("0.0").format(result));
        }

        return rankingTableDto;
    }


    //更新红榜次数
    @Override
    public Integer updateRedTable(String itemName, Integer redNumber) {
        //创建更新对象
        ProjectScoreWeek projectScoreWeek1 = new ProjectScoreWeek();
        projectScoreWeek1.setRedTableNum(redNumber);
        //更新时间
        projectScoreWeek1.setInsertTime(new Date());

        //创建更新条件
        UpdateWrapper<ProjectScoreWeek> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("item_name",itemName);
        //进行更新
        return projectScoreWeekMapper.update(projectScoreWeek1,updateWrapper);
    }


    //更新黑榜次数
    @Override
    public Integer updateBlackTable(String itemName, Integer blackNum) {
        //创建更新对象
        ProjectScoreWeek projectScoreWeek2 = new ProjectScoreWeek();
        //更新次数
        projectScoreWeek2.setBlackTableNum(blackNum);
        //更新时间
        projectScoreWeek2.setInsertTime(new Date());
        //创建更新条件
        UpdateWrapper<ProjectScoreWeek> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("item_name",itemName);
        //进行更新
        return projectScoreWeekMapper.update(projectScoreWeek2,updateWrapper);
    }

    @Override
    public Float getLastweekAvgScore() {
        return projectScoreWeekMapper.getLastweekAvgScore();
    }

    @Override
    public List<ProjectScoreWeek> selectScoreRedTable() {
        return projectScoreWeekMapper.selectScoreRedTable();
    }

    @Override
    public List<ProjectScoreWeek> selectScoreBlackTable() {
        return projectScoreWeekMapper.selectScoreBlackTable();
    }
}
