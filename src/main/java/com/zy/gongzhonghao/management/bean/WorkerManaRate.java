package com.zy.gongzhonghao.management.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import java.util.Date;

//记录工人培训率和管理人员到岗率
@Data
public class WorkerManaRate {

    @TableId(type = IdType.AUTO)
    private Integer id;

    //统计日期
    private Date StatisticsDate;

    //工人培训率
    private Integer workerTrainRate;

    //管理人员到岗率
    private Integer manaDutyRate;

    //获取时间
    private Date insertTime;

}
