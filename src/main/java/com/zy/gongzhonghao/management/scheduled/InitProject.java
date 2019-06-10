package com.zy.gongzhonghao.management.scheduled;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * Created by ych on 2019/5/7.
 * 项目启动时，执行一次
 */
@Component
public class InitProject  implements ApplicationRunner {

    @Autowired
    private ScheduledTask scheduledTask;

    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        //scheduledTask.totalRequData1();
       // scheduledTask.projectScoreWeek();
        //scheduledTask.test();
    }
}
