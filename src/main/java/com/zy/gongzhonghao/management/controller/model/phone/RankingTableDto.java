package com.zy.gongzhonghao.management.controller.model.phone;


import com.zy.gongzhonghao.management.bean.BlackRanking;
import com.zy.gongzhonghao.management.bean.ProjectScoreWeek;
import com.zy.gongzhonghao.management.bean.RedRanking;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class RankingTableDto {

    //安全评估得分
    private String safetyScore;
    //红榜
    private List<ProjectScoreWeek>  redTable;
    //黑榜
    private List<ProjectScoreWeek> blackTable;
    //红榜排行次数
    private List<RedRanking> redRankingList;
    //黑帮排行次数
    private List<BlackRanking> blackRankingList;
}
