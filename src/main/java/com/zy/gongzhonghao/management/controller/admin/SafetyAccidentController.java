package com.zy.gongzhonghao.management.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zy.gongzhonghao.management.bean.AccidentNumTable;
import com.zy.gongzhonghao.management.controller.model.safetyaccident.SafetyAccidentCreateRequest;
import com.zy.gongzhonghao.management.service.AccidentNumService;
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

@RestController
@RequestMapping("/admin/safetyAccident")
public class SafetyAccidentController extends BaseController {

    @Autowired
    private AccidentNumService accidentNumService;

    //安全事故数据查询
    @RequestMapping("/page")
    public Object safetyQueryPage(Integer pagesize, Integer pageno){
        star();
        try{
            Map<String,Object> paramMap = new HashMap<>();
            paramMap.put("pagesize",pagesize);
            paramMap.put("pageno",pageno);
            //查询数据
            Page<AccidentNumTable> page = accidentNumService.safetyQueryPage(paramMap);
            MyPage<AccidentNumTable> accidentNumMyPage = new MyPage<>();
            //每页数量
            accidentNumMyPage.setPagesize((int)page.getSize());

            //获取所有的分页数据
            List<AccidentNumTable> records1 = page.getRecords();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            for (int i = 0; i< records1.size(); i++){
                Date safetyDate = records1.get(i).getAccidentDate();
                String formatDate = sdf.format(safetyDate);

                records1.get(i).setStringDate(formatDate);
            }
            //每页记录数
            accidentNumMyPage.setDatas(records1);
            //当前页
            accidentNumMyPage.setPageno((int) page.getCurrent());
            //总数量
            accidentNumMyPage.setTotalsize((int)page.getTotal());
            //将数据存储到result中
            data(accidentNumMyPage);
            success(true);
        }catch (Exception e){
            e.printStackTrace();
            success(false);
        }
        return end();
    }


    @PostMapping("/addAction")
    public Object addAction(@RequestBody SafetyAccidentCreateRequest safetyAccidentCreateRequest) {
        star();
        String accidentDate = safetyAccidentCreateRequest.getAccidentDate();
        Integer accidentNum = safetyAccidentCreateRequest.getAccidentNum();
        String submitName = safetyAccidentCreateRequest.getSubmitName();

        AccidentNumTable accidentModel = new AccidentNumTable();
        try {
            accidentModel.setAccidentDate(DateUtils.parseDate(accidentDate,"yyyy-MM-dd"));
        } catch (ParseException e) {
            e.printStackTrace();
            success(false);
            return end();
        }
        accidentModel.setAccidentNum(accidentNum);
        accidentModel.setSubmitName(submitName);
        accidentModel.setSubmitTime(new Date());
        //进行插入
        Integer insertResult = accidentNumService.insertAccidentNum(accidentModel);

        if(1 == insertResult) {
            success(true);
        }else {
            success(false);
        }
        return end();
    }



    //删除数据
    @PostMapping("deleteById")
    public Object deleteById(Integer deleteId){
        star();
        //调用删除方法
        Integer result = accidentNumService.deleteById(deleteId);
        if(1 == result){
            success(true);
        }else {
            success(false);
        }
        return end();
    }
}
