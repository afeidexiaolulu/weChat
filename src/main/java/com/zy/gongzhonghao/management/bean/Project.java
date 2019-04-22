package com.zy.gongzhonghao.management.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

/**
 * 项目表
 */
@Data
public class Project {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String itemName;

    private String itemNo;

    private boolean status;

    private Date insertTime;

}
