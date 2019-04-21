package com.zy.gongzhonghao.management.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zy.gongzhonghao.management.bean.AccidentNumTable;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public interface AccidentNumMapper extends BaseMapper<AccidentNumTable> {

    List<AccidentNumTable> seletcDataAndDateLast3Month();

}
