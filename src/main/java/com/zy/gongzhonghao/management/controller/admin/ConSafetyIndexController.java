package com.zy.gongzhonghao.management.controller.admin;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zy.gongzhonghao.management.bean.SafetyIndex;
import com.zy.gongzhonghao.management.controller.model.consafetyindex.ConSafetyIndexCreateRequest;
import com.zy.gongzhonghao.management.service.SafetyIndexService;
import com.zy.gongzhonghao.management.util.MyPage;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 施工安全指数的控制器
 */
@RestController
@RequestMapping("/admin/consafetyindex")
public class ConSafetyIndexController extends BaseController {

    @Autowired
    private SafetyIndexService safetyIndexService;

    //分页查询
    @PostMapping("/page")
    public Object consafetyindexQueryPage(Integer pagesize, Integer pageno){
        star();

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("pageno",pageno);
        paramMap.put("pagesize",pagesize);
        try{
            //返回分页对象
            Page<SafetyIndex> page = safetyIndexService.queryPage(paramMap);
            //将分页对象进行赋值
            MyPage<SafetyIndex> safetyIndexMyPage = new MyPage<>();
            safetyIndexMyPage.setPagesize((int) page.getSize());
            safetyIndexMyPage.setTotalsize((int)page.getTotal());
            safetyIndexMyPage.setPageno((int)page.getCurrent());
            //获取所有的分页数据
            List<SafetyIndex> records = page.getRecords();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            for (int i = 0; i< records.size(); i++){
                Date safetyDate = records.get(i).getSafetyDate();
                String formatDate = sdf.format(safetyDate);
                safetyDate = sdf.parse(sdf.format(safetyDate));
                records.get(i).setStringDate(formatDate);
            }
            safetyIndexMyPage.setDatas(records);
            success(true);
            data(safetyIndexMyPage);
        }catch (Exception e){
            e.printStackTrace();
            success(false);
        }
        //返回分页信息
        return end();
    }


    @PostMapping("/addAction")
    public Object addAction(@RequestBody ConSafetyIndexCreateRequest conSafetyIndexCreateRequest){
        star();
        String safetyDate = conSafetyIndexCreateRequest.getSafetyDate();
        Float safetyIndex = conSafetyIndexCreateRequest.getSafetyIndex();
        String submitName = conSafetyIndexCreateRequest.getSubmitName();

        //创建施工安全指数对象
        SafetyIndex safetyIndex1 = new SafetyIndex();
        try {
            //对数据库pojo对象赋值
            safetyIndex1.setSafetyDate(DateUtils.parseDate(safetyDate,"yyyy-MM-dd"));
            safetyIndex1.setSafetyNum(safetyIndex);
            safetyIndex1.setSubmitName(submitName);
            safetyIndex1.setSubmitTime(new Date());
        } catch (ParseException e) {
            e.printStackTrace();
            success(false);
            return end();
        }
        Integer result = safetyIndexService.insertSafetyIndex(safetyIndex1);
        if(result == 1){
            success(true);
        }else {
            success(false);
        }
        return end();
    }

    //根据id删除
    @PostMapping("/deleteById")
    public Object deleteById(Integer deleteId){
        star();
        try {
            Integer result = safetyIndexService.deleteById(deleteId);
                if(1 == result){
                    success(true);
                }else {
                    success(false);
                }
                return end();
        }catch (Exception e){
            e.printStackTrace();
            success(false);
            return end();
        }
    }

}
