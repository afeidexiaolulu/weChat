package com.zy.gongzhonghao.management.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zy.gongzhonghao.management.bean.BlackRanking;

import java.util.Date;

public interface BlackRankingService extends IService<BlackRanking> {
    //插入黑榜前5名
    Integer insertBlackTable();
}
