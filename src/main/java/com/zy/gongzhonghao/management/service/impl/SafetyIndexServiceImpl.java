package com.zy.gongzhonghao.management.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zy.gongzhonghao.management.bean.ProjectRate;
import com.zy.gongzhonghao.management.bean.SafetyIndex;
import com.zy.gongzhonghao.management.bean.TotalSafetyData;
import com.zy.gongzhonghao.management.controller.model.phone.SafetyIndexDto;
import com.zy.gongzhonghao.management.mapper.SafetyIndexMapper;
import com.zy.gongzhonghao.management.service.ProjectRateService;
import com.zy.gongzhonghao.management.service.SafetyIndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class SafetyIndexServiceImpl extends ServiceImpl<SafetyIndexMapper, SafetyIndex> implements SafetyIndexService {

    @Value("${Mmin}")
    private Integer Mmin;

    @Value("${Mmax}")
    private Integer Mmax;

    @Value("${a1}")
    private Float al;

    @Value("${a2}")
    private Float a2;

    @Value("${a3}")
    private Float a3;

    @Autowired
    private ProjectRateService projectRateService;

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
            safetyIndexDto.setSafetyIndex(safetyIndex[size-1]);
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

    //通过接口插入安全指数
    @Override
    public Integer insertSafetyIndexByInterface(List<TotalSafetyData> totalSafetyDataList, Integer diff) {

        Float safetIndexSum = new Float("0");

        //遍历list集合，求出每个项目安全指数
        for(int i=0; i< totalSafetyDataList.size(); i++){
            float workerRate;
            float manaRate;
            float manaBachelor;
            //求培训率
            TotalSafetyData totalSafetyData = totalSafetyDataList.get(i);
            //工人总人数
            Integer workerOnJobCount = totalSafetyData.getWorkerOnJobCount();
            //工人教育人数
            Integer workerEduCount = totalSafetyData.getWorkerEduCount();
            if(workerEduCount  == 8888 || workerEduCount == 0 || workerOnJobCount == 0 || workerOnJobCount == 0 ){
                workerRate = 0;
            }else {
                //工人教育率
                workerRate = workerEduCount / new Float(workerOnJobCount);
            }

            //求到岗率
            Integer managerOnJobCount = totalSafetyData.getManagerOnJobCount();
            Integer managerAttCount = totalSafetyData.getManagerAttCount();
            if(managerOnJobCount == 0 || managerOnJobCount == 8888 || managerAttCount == 8888 || managerAttCount == 0){
                //到岗率
                manaRate = 0;
            }else {
                //到岗率
                manaRate = managerAttCount / new Float(managerOnJobCount);
            }
            //求所有的项目管理指数
            List<ProjectRate> projectRateList =  projectRateService.selectAll();
            if(i<202){ 
                float manaBachelorF = projectRateList.get(i).getManaBachelor();
                if(manaBachelorF == 8888){
                    manaBachelor = new Float(0);
                }else {
                    manaBachelor = manaBachelorF/10;
                }
            }else {
                //如果超过202，为0;
                manaBachelor = new Float(0);
            }
            //计算项目的安全指数
            safetIndexSum += ((Mmax - Mmin) / (al + a2 + a3)) * ((al * workerRate) + (a2 * manaBachelor) + (a3 * manaRate)) + 60;
            System.out.println(safetIndexSum);
        }
        //求区域安全指数
        float safetIndexAvg = safetIndexSum / totalSafetyDataList.size();
        //生成昨天时间
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE,diff);
        Date time = calendar.getTime();

        SafetyIndex areaSafetyIndex = new SafetyIndex(null, time, safetIndexAvg, "系统生成", new Date(), null);
        //插入区域安全指数
        return baseMapper.insert(areaSafetyIndex);
    }
}
