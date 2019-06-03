package com.zy.gongzhonghao.management.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.zy.gongzhonghao.management.bean.RedRanking;

import java.util.Date;

public interface RedRankingService extends IService<RedRanking> {
    //插入红包
    Integer insertRedTable();
}
