package com.zy.gongzhonghao.management.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zy.gongzhonghao.management.bean.BlackRanking;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;
import java.util.List;



@Component
public interface BlackRankingMapper extends BaseMapper<BlackRanking> {
    //黑榜次数前5名
    @Select("select item_name, black_ranking_num from black_ranking ORDER BY black_ranking_num desc LIMIT 0,5")
    List<BlackRanking> selectBlack5SumMax();

    //选择上黑榜次数
    @Select("select black_ranking_num from black_ranking where item_name = #{itemName}")
    Integer selectBlackNumByItemName(String itemName);
}
