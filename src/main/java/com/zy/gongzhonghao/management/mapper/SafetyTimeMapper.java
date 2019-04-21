package com.zy.gongzhonghao.management.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zy.gongzhonghao.management.bean.SafetyTimeTable;
import org.springframework.stereotype.Component;

@Component
public interface SafetyTimeMapper extends BaseMapper<SafetyTimeTable> {
    Integer selectSafetyTime();
}
