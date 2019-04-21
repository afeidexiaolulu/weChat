package com.zy.gongzhonghao.management.bean;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import java.util.Date;

/**
 * 此类为安全事故数的实体类
 */
@Data
public class AccidentNumTable {

    //安全工时,表明不是数据库字段，不参与映射
    @TableField(exist = false)
    private Integer safetyTime;

    //最高安全记录
    @TableField(exist = false)
    private Integer safetyTimeBig;

    //安全事故数表的主键，主键自增
    @TableId(type = IdType.AUTO)
    private Integer id;

    //事故时间
    private Date accidentDate;

    //事故数
    private Integer accidentNum;

    //上传人
    private String submitName;

    //上传时间
    private Date submitTime;

    //回显字段
    @TableField(exist = false)
    private String stringDate;

}
