/*
 * @Copy.Right (c)2018 greatgas.cn
 * @Date 2018年8月16日 下午4:10:19
 */
package com.madder.attendance;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * 
 * @author yinshijie
 */
public class AttendanceServletInitializer extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(AttendanceStarter.class);
    }
    
}
