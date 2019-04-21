package com.zy.gongzhonghao.management.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zy.gongzhonghao.management.bean.AccidentNumTable;
import com.zy.gongzhonghao.management.mapper.AccidentNumMapper;
import com.zy.gongzhonghao.management.service.AccidentNumService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AccidentNumServiceImpl extends ServiceImpl<AccidentNumMapper, AccidentNumTable> implements AccidentNumService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccidentNumServiceImpl.class);
    @Autowired
    private AccidentNumMapper accidentNumMapper;

    //分页查询
    @Override
    public Page<AccidentNumTable> safetyQueryPage(Map<String, Object> paramMap) {
        Integer pageno = (Integer)paramMap.get("pageno");
        Integer pagesize = (Integer)paramMap.get("pagesize");
        Page<AccidentNumTable> accidentNumPage = new Page<>(pageno, pagesize);
        QueryWrapper<AccidentNumTable> accidentNumQueryWrapper = new QueryWrapper<>();
        accidentNumQueryWrapper.orderByDesc("id");
        try {
            accidentNumMapper.selectPage(accidentNumPage, accidentNumQueryWrapper);
            return accidentNumPage;
        }catch (Exception e){
            LOGGER.debug("查询安全事故分页数据失败");
            throw new RuntimeException();
        }
    }

    //插入安全事故数
    @Override
    public Integer insertAccidentNum(AccidentNumTable accidentModel) {
        try {
            return baseMapper.insert(accidentModel);
        } catch (Exception e) {
            LOGGER.debug("插入安全事故数失败");
            throw new RuntimeException();
        }
    }

    //删除安全事故数
    @Override
    public Integer deleteById(Integer deleteId) {
        try {
            return baseMapper.deleteById(deleteId);
        } catch (Exception e) {
            LOGGER.debug("删除安全事故数据失败");
            throw new RuntimeException();
        }
    }
}