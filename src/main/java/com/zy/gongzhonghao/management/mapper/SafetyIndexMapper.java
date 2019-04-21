package com.zy.gongzhonghao.management.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zy.gongzhonghao.management.bean.SafetyIndex;
import com.zy.gongzhonghao.management.controller.model.phone.SafetyIndexDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface SafetyIndexMapper extends BaseMapper<SafetyIndex> {
    //按照统计日期选择最后一个
    SafetyIndex selectLastOne();

    //按照统计日期选择最后10天安全指数
    List<SafetyIndex> selectLast10SaIndex();
}
