package com.zy.gongzhonghao.management.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zy.gongzhonghao.management.bean.WorkerManaRate;
import org.springframework.stereotype.Component;
import java.util.List;

/**
 * 工人，管理人员到岗率mapper
 */
@Component
public interface WorkerManaRateMapper extends BaseMapper<WorkerManaRate> {

    //查询最近10天工人培训率和到岗率
    List<WorkerManaRate> selectLast10day();


}
