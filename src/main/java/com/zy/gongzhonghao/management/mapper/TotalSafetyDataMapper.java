package com.zy.gongzhonghao.management.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zy.gongzhonghao.management.bean.TotalSafetyData;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public interface TotalSafetyDataMapper extends BaseMapper<TotalSafetyData> {


    //获取昨天工人人数
    Integer getYesWorkerNum(String date);

    //获取昨天培训工人人数
    Integer getYesWorkerTraNum(String yesterday);

    //昨天管理人员总人数
    Integer getYesManaNum(String yesterday);

    //昨天管理人员考勤人数
    Integer getYesManaDutyNum(String yesterday);

    //批量插入
    Integer insertJsonTableBatch(@Param("totalSafetyDataList") List<TotalSafetyData> totalSafetyDataList);


    //查询泥头车预警平均数
    @Select("select avg(Car_warning) from total_safety_data where date(Statistics_date) = #{date} and Car_warning != 8888")
    Float getCarWarningNumAVG(String yesterday);

    //查询塔吊预警平均数
    @Select("select avg(Crane_weight) from total_safety_data where date(Statistics_date) = #{date} and Crane_weight != 8888")
    Float getCraneWeightNumAVG(String yesterday);

    //查询升降机预警平均数
    @Select("select avg(Lifter_weight) from total_safety_data where date(Statistics_date) = #{date} and Lifter_weight != 8888")
    Float getLifterWeightNumAVG(String yesterday);

    //查询扬尘预警平均数
    @Select("select avg(Dust_warning) from total_safety_data where date(Statistics_date) = #{date} and Dust_warning != 8888")
    Float getDustWarningNumAVG(String yesterday);

    //查询噪音预警平均数
    @Select("select avg(Noise_warning) from total_safety_data where date(Statistics_date) = #{date} and Noise_warning != 8888")
    Float getNoiseWarningNumAVG(String yesterday);

}
