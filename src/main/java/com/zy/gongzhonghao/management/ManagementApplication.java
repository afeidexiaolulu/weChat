package com.zy.gongzhonghao.management;



import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@EnableScheduling
//@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableTransactionManagement    //事务
@SpringBootApplication
@MapperScan("com.zy.gongzhonghao.management.mapper")
public class ManagementApplication {
    public static void main(String[] args) {
        SpringApplication.run(ManagementApplication.class, args);
    }


}
