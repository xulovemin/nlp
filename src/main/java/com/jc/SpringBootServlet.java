package com.jc;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@MapperScan(value = "com.jc.*.dao")
public class SpringBootServlet extends SpringBootServletInitializer {

    // jar启动
    public static void main(String[] args) {
        SpringApplication.run(SpringBootServlet.class, args);
    }

    // tomcat war启动
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(SpringBootServlet.class);
    }

}
