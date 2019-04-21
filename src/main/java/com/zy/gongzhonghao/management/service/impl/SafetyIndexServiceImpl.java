package com.zy.gongzhonghao.management.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zy.gongzhonghao.management.bean.SafetyIndex;
import com.zy.gongzhonghao.management.controller.model.phone.SafetyIndexDto;
import com.zy.gongzhonghao.management.mapper.SafetyIndexMapper;
import com.zy.gongzhonghao.management.service.SafetyIndexService;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

@Service
public class SafetyIndexServiceImpl extends ServiceImpl<SafetyIndexMapper, SafetyIndex> implements SafetyIndexService {

    @Autowired
    private SafetyIndexMapper safetyIndexMapper;


    @Override
    public Page<SafetyIndex> queryPage(Map<String, Object> paramMap) {


        Integer pagesize = (Integer) paramMap.get("pagesize");
        Integer pageno = (Integer) paramMap.get("pageno");

        Page<SafetyIndex> safetyIndexPage = new Page<>(pageno, pagesize);
        QueryWrapper<SafetyIndex> safetyIndexQueryWrapper = new QueryWrapper<>();
        safetyIndexQueryWrapper.orderByDesc("safety_date");
        safetyIndexMapper.selectPage(safetyIndexPage,safetyIndexQueryWrapper);
        return safetyIndexPage;
    }

    //插入安全指数
    @Override
    public Integer insertSafetyIndex(SafetyIndex safetyIndex1) {
        Integer result = baseMapper.insert(safetyIndex1);
        return result;
    }

    //通过id删除施工安全指数
    @Override
    public Integer deleteById(Integer id) {
        return baseMapper.deleteById(id);
    }

    //按照日期排序，选择最近10天安全指数
    @Override
    public SafetyIndexDto selectLast10SaIndex() {
        SafetyIndexDto safetyIndexDto = new SafetyIndexDto();

        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd");
        //选出最近10天安全指数
        List<SafetyIndex> safetyIndexList = safetyIndexMapper.selectLast10SaIndex();
        //查询出来的结果长度
        int size = safetyIndexList.size();
        if(size != 0){

            //创建同等长度数组
            Float safetyIndex[] = new Float[size];
            String safetyDate[] = new String[size];
            for(int i=0;i<size;i++){
                safetyIndex[i] = safetyIndexList.get(size-1-i).getSafetyNum();
                safetyDate[i]=sdf.format(safetyIndexList.get(size-1-i).getSafetyDate());
            }
            //给最新安全指数负责
            safetyIndexDto.setSafetyIndex(safetyIndex[0]);
            //安全指数数组
            safetyIndexDto.setSafetyIndexArr(safetyIndex);
            safetyIndexDto.setSafetyIndexDateArr(safetyDate);
            return safetyIndexDto;
        }else {
            SafetyIndexDto dto = new SafetyIndexDto();
            dto.setSafetyIndex(new Float(0));
            return dto;
        }
    }
}
