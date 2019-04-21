package com.zy.gongzhonghao.management.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zy.gongzhonghao.management.bean.TotalWarning;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public interface TotalWarningMapper extends BaseMapper<TotalWarning> {

    //返回近10天的所有预警
    List<TotalWarning> selectTotalWarningList();
}
