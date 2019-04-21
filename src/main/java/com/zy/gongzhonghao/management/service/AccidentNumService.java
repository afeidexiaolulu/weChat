package com.zy.gongzhonghao.management.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zy.gongzhonghao.management.bean.AccidentNumTable;
import java.util.Map;


public interface AccidentNumService extends IService<AccidentNumTable> {

    //查询所有的分页数据并返回
    Page<AccidentNumTable> safetyQueryPage(Map<String, Object> paramMap);

    //插入安全事故数据
    Integer insertAccidentNum(AccidentNumTable accidentModel);

    //通过id删除某条数据
    Integer deleteById(Integer deleteId);



}
