package com.madder.attendance;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication //(exclude = DataSourceAutoConfiguration.class)    //等同于 @Configuration @EnableAutoConfiguration @ComponentScan
@EnableTransactionManagement
@MapperScan(basePackages = {"com.madder.attendance.dao"})
@ServletComponentScan
public class AttendanceStarter {

    public static void main(String[] args) {
        SpringApplication.run(AttendanceStarter.class, args);
    }

}
