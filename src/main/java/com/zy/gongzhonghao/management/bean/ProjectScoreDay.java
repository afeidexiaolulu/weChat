package com.zy.gongzhonghao.management.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectScoreDay {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String itemName;

    private String  itemNo;

    private Date statisticsDate;

    private Integer craneWeight;

    private Integer liferWeight;

    private Integer carWarning;

    private Integer dustWarning;

    private Integer noiseWarning;

    //管理培训率
    private Float manaRate;

    //工人培训率
    private Float workerRate;

    private Float score;

    private Date insertTime;
}
