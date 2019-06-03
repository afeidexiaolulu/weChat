package com.zy.gongzhonghao.management.bean;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.util.Date;

/**
 * 此bean为三方接口传递过来的数据的实体类
 */
@Data
public class TotalSafetyData {

    @TableId(type = IdType.AUTO)
    private Integer id;

    //统计日期
    private Date StatisticsDate;

    //项目名称
    private String ItemName;

    //安全监督指数
    private String ItemNo;

    //工人总数量
    private Integer  WorkerOnJobCount;

    //接受培训工人数量
    private Integer WorkerEduCount;

    //管理人员总数
    private Integer ManagerOnJobCount;

    //管理人员考勤数量
        private Integer ManagerAttCount;

    //工人考勤数
    private Integer WorkerAttCount;

    //塔吊预警指数
    private Integer CraneWeight;

    //升降机预警指数
    private Integer LifterWeight;

    //卡车预警指数
    private Integer CarWarning;

    //扬尘预警指数
    private Integer DustWarning;

    //噪音预警指数
    private Integer NoiseWarning;

}
