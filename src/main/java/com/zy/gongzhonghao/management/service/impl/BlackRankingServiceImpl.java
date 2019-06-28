package com.zy.gongzhonghao.management.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zy.gongzhonghao.management.bean.BlackRanking;
import com.zy.gongzhonghao.management.bean.ProjectScoreWeek;
import com.zy.gongzhonghao.management.mapper.BlackRankingMapper;
import com.zy.gongzhonghao.management.service.BlackRankingService;
import com.zy.gongzhonghao.management.service.ProjectScoreWeekService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class BlackRankingServiceImpl extends ServiceImpl<BlackRankingMapper, BlackRanking> implements BlackRankingService {

    @Autowired
    private BlackRankingMapper blackRankingMapper;

    @Autowired
    private ProjectScoreWeekService projectScoreWeekService;


    //插入黑榜前5名
    @Override
    public Integer insertBlackTable() {
        //选出本周最后5名
        //List<ProjectScoreWeek> blackTables = projectScoreWeekService.select5BlackTable();

        List<ProjectScoreWeek> blackTables = projectScoreWeekService.selectScoreBlackTable();
        for(int i=0; i< blackTables.size(); i++){
            //进行插入
            QueryWrapper<BlackRanking> wrapper = new QueryWrapper<>();
            wrapper.eq("item_name",blackTables.get(i).getItemName());
            BlackRanking blackRanking = baseMapper.selectOne(wrapper);
            if(blackRanking == null){
                //如果为null，插入
                blackRankingMapper.insert(new BlackRanking(null, blackTables.get(i).getItemName(),1,new Date()));
                projectScoreWeekService.updateBlackTable(blackTables.get(i).getItemName(),1);
            }else {
                //更新次数
                BlackRanking blackRankingUp = new BlackRanking();
                blackRankingUp.setBlackRankingNum(blackRanking.getBlackRankingNum()+1);
                blackRankingUp.setInsertTime(new Date());
                //如果不为1，次数加1
                UpdateWrapper<BlackRanking> updateWrapper = new UpdateWrapper<>();
                updateWrapper.eq("item_name",blackRanking.getItemName());
                blackRankingMapper.update(blackRankingUp,updateWrapper);
                projectScoreWeekService.updateBlackTable(blackTables.get(i).getItemName(),blackRanking.getBlackRankingNum()+1);
            }
        }
        return 1;
    }
}
