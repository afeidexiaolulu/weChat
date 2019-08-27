package com.zy.gongzhonghao.management.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zy.gongzhonghao.management.bean.ProjectScoreDay;
import com.zy.gongzhonghao.management.bean.ProjectScoreWeek;
import com.zy.gongzhonghao.management.bean.TotalSafetyData;
import com.zy.gongzhonghao.management.mapper.BlackRankingMapper;
import com.zy.gongzhonghao.management.mapper.ProjectScoreDayMapper;
import com.zy.gongzhonghao.management.mapper.ProjectScoreWeekMapper;
import com.zy.gongzhonghao.management.mapper.RedRankingMapper;
import com.zy.gongzhonghao.management.service.ProjectScoreDayService;
import com.zy.gongzhonghao.management.util.DateUtils;
import com.zy.gongzhonghao.management.util.MyPage;
import org.apache.commons.logging.Log;
import org.apache.ibatis.annotations.Param;
import org.apache.velocity.runtime.directive.Foreach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ProjectScoreDayServiceImpl extends ServiceImpl<ProjectScoreDayMapper, ProjectScoreDay> implements ProjectScoreDayService {

    @Autowired
    private ProjectScoreDayMapper projectScoreDayMapper;

    @Autowired
    private ProjectScoreWeekMapper projectScoreWeekMapper;

    @Autowired
    private RedRankingMapper redRankingMapper;

    @Autowired
    private BlackRankingMapper blackRankingMapper;

    //批量插入每天每个项目安全指数
    @Override
    public Integer insertBatch(List<ProjectScoreDay> projectScoreDayList) {
        return projectScoreDayMapper.insertBatch(projectScoreDayList);
    }


    //求出每个项目每周平均分并插入
    @Override
    public Integer insertRankingTable(List<TotalSafetyData> totalSafetyDataList, Integer diff) {
        //每周平均分数list
        List<ProjectScoreWeek> weekList = new ArrayList<>();
        //分别求出每个项目每周平均安全指数
        for (TotalSafetyData totalSafetyData : totalSafetyDataList) {
            String itemNo = totalSafetyData.getItemNo();

            //上周日的日期
            String endDateStr = DateUtils.getDateStr(diff,"yyyy-MM-dd");
            endDateStr += " 00:00:00";

            //上周一日期
            String startDateStr = DateUtils.getDateStr(diff-6, "yyyy-MM-dd");
            startDateStr += " 00:00:00";

            //求出每个项目上周平均数据
            Float weekScore = projectScoreDayMapper.getAvgScoreWeek(itemNo,startDateStr,endDateStr);

            //求出每个项目上红榜次数
            Integer redNum = redRankingMapper.selectRedNumByItemName(totalSafetyData.getItemName());
            if(redNum == null){
                //赋值为0
                redNum = 0;
            }
            //求出每个项目上黑榜次数
            Integer blackNum = blackRankingMapper.selectBlackNumByItemName(totalSafetyData.getItemName());
            if(blackNum == null){
                //赋值为0
                blackNum = 0;
            }
            //创建每周分数平均对象
            weekList.add(new ProjectScoreWeek(null,totalSafetyData.getItemName(),totalSafetyData.getItemNo(),totalSafetyData.getStatisticsDate(),weekScore,redNum,blackNum,new Date()));
        }

        //批量插入每周平均数
        return projectScoreWeekMapper.insertBatchWeek(weekList);
    }

    //删除上周每天所有安全数据
    @Override
    public Integer deleteAll() {
        return baseMapper.delete(null);
    }

    //返回有每日安全分数的日期列表
    @Override
    public List<String> getSelectDateList() {
        List<Date> selectDateList = projectScoreDayMapper.getSelectDateList();

        //日期格式化
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        List<String> dateList = new ArrayList<>();
        for(int i = selectDateList.size()-1; i >= 0; i--){
            Object myDate = selectDateList.get(i);
            String date = sdf.format(myDate);
            dateList.add(date);
        }
        return dateList;
    }

    //查询最新的日期的每日项目列表
    @Override
    public MyPage<ProjectScoreDay> queryPage(Map<String, Object> paramMap) {

        //查询所有日期列表
        List<Date> selectDateList = projectScoreDayMapper.getSelectDateList();
        //最新日期
        Date lastDate = selectDateList.get(selectDateList.size() - 1);

        Integer pageSize = (Integer) paramMap.get("pageSize");
        Integer pageNo = (Integer) paramMap.get("pageNo");
        Page<ProjectScoreDay> projectScoreDayPage = new Page<>(pageNo, pageSize);
        QueryWrapper<ProjectScoreDay> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("statistics_date",lastDate);
        queryWrapper.orderByDesc("score");
        queryWrapper.orderByAsc("id");
        projectScoreDayMapper.selectPage(projectScoreDayPage,queryWrapper);
        //创建分页对象
        MyPage<ProjectScoreDay> myPage = new MyPage<>();
        myPage.setPageno((int)projectScoreDayPage.getCurrent());
        myPage.setPagesize((int)projectScoreDayPage.getSize());
        myPage.setTotalsize((int)projectScoreDayPage.getTotal());
        //获取所有的分页数据
        List<ProjectScoreDay> records = projectScoreDayPage.getRecords();
        myPage.setDatas(records);
        return myPage;
    }

    @Override
    public MyPage<ProjectScoreDay> queryPageByCondition(Map<Object, Object> paramMap) {
        //将查询参数取出
        Integer pageSize = (int)paramMap.get("pageSize");
        Integer pageNo = (int) paramMap.get("pageNo");
        String queryDate = (String)paramMap.get("queryDate");
        queryDate += " 00:00:00";
        String queryWord = (String) paramMap.get("queryWord");
        //进行查询  创建page对象
        Page<ProjectScoreDay> projectScoreDayPage = new Page<>(pageNo, pageSize);
        //创建queryWrapper对象
        QueryWrapper<ProjectScoreDay> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("statistics_date",queryDate);
        queryWrapper.like("item_name",queryWord);
        queryWrapper.orderByAsc("rank_num_t");
        projectScoreDayMapper.selectPage(projectScoreDayPage,queryWrapper);
        //将查询好的page对象封装为自定义page对象
        MyPage<ProjectScoreDay> objectMyPage = new MyPage<>();
        //当前页码
        objectMyPage.setPageno((int)projectScoreDayPage.getCurrent());
        //每页数量
        objectMyPage.setPagesize((int)projectScoreDayPage.getSize());
        //总数据量
        objectMyPage.setTotalsize((int)projectScoreDayPage.getTotal());
        //数据
        objectMyPage.setDatas(projectScoreDayPage.getRecords());
        return objectMyPage;
    }


    @Override
    public List<ProjectScoreDay> selectProjectListByDate(Date statisticsDate) {
        return projectScoreDayMapper.selectProjectListByDate(statisticsDate);
    }

    //批量更新每日项目表里的排名字段
    @Override
    public Integer updateRankNumBatch(List<ProjectScoreDay> projectScoreDays) {
        return projectScoreDayMapper.updateRankNumBatch(projectScoreDays);
    }

    //通过itemNo和日期查询项目分数详情
    @Override
    public ProjectScoreDay selectProjectByItemNoAndDate(String itemNo, String statisticsDate) {
        //装换为与数据库格式一致
        statisticsDate += " 00:00:00";
        return projectScoreDayMapper.selectProjectByItemNoAndDate(itemNo, statisticsDate);
    }


}
