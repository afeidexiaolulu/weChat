package com.zy.gongzhonghao.management.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zy.gongzhonghao.management.bean.RedRanking;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface RedRankingMapper extends BaseMapper<RedRanking> {
    //红榜最多的5个
    @Select("select item_name, red_ranking_num from red_ranking ORDER BY red_ranking_num desc LIMIT 0,5")
    List<RedRanking> selectRed5SumMax();

    //根据itemName选择红榜次数
    @Select("select red_ranking_num from red_ranking  where item_name = #{itemName}")
    Integer selectRedNumByItemName(String itemName);
}
