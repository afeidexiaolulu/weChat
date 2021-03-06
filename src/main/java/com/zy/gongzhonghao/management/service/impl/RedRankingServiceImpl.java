package com.zy.gongzhonghao.management.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zy.gongzhonghao.management.bean.ProjectScoreWeek;
import com.zy.gongzhonghao.management.bean.RedRanking;
import com.zy.gongzhonghao.management.mapper.RedRankingMapper;
import com.zy.gongzhonghao.management.service.ProjectScoreWeekService;
import com.zy.gongzhonghao.management.service.RedRankingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class RedRankingServiceImpl extends ServiceImpl<RedRankingMapper, RedRanking> implements RedRankingService {

    @Autowired
    private RedRankingMapper redRankingMapper;

    @Autowired
    private ProjectScoreWeekService projectScoreWeekService;


    //插入红榜
    @Override
    public Integer insertRedTable() {
        //选出本周前5名
        //List<ProjectScoreWeek> redTables = projectScoreWeekService.select5RedTable();
        List<ProjectScoreWeek> redTables = projectScoreWeekService.selectScoreRedTable();
        for(int i=0; i< redTables.size(); i++){
            //进行插入
            QueryWrapper<RedRanking> wrapper = new QueryWrapper<>();
            wrapper.eq("item_name",redTables.get(i).getItemName());
            RedRanking redRanking = redRankingMapper.selectOne(wrapper);
            if(redRanking == null){
                //如果为null，插入
                redRankingMapper.insert(new RedRanking(null, redTables.get(i).getItemName(),1,new Date()));
                //更新周榜中红榜次数
                projectScoreWeekService.updateRedTable(redTables.get(i).getItemName(),1);
            }else {

                RedRanking redRankingUp = new RedRanking();
                //如果不为1，次数加1
                redRankingUp.setRedRankingNum(redRanking.getRedRankingNum()+1);
                redRankingUp.setInsertTime(new Date());

                UpdateWrapper<RedRanking> updateWrapper = new UpdateWrapper<>();
                updateWrapper.eq("item_name",redRanking.getItemName());
                //更新红榜中次数
                redRankingMapper.update(redRankingUp,updateWrapper);
                //更新周榜中红榜次数
                projectScoreWeekService.updateRedTable(redTables.get(i).getItemName(),redRanking.getRedRankingNum()+1);
            }

        }
        return 1;
    }

}
