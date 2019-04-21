package com.zy.gongzhonghao.management.bean;

import lombok.Data;

import java.util.Date;

/**
 * 项目表
 */
@Data
public class Project {

    private Integer id;

    private String itemName;

    private String itemNo;

    private boolean status;

    private Date insertTime;

}
