package com.zy.gongzhonghao.management.bean;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class SafetyTimeMax {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private Integer maxRecord;

}
