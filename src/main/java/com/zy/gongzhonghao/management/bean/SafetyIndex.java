package com.zy.gongzhonghao.management.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 施工安全指数表的实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SafetyIndex {
    //主键自增
    @TableId(type = IdType.AUTO)
    private Integer id;

    //安全指数发布日期
    private Date safetyDate;

    //安全指数
    private Float safetyNum;

    //提交人
    private String submitName;

    //提交时间
    private Date submitTime;

    @TableField(exist = false)
    private String stringDate;


}
