package com.hq.hqusercenter;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication(scanBasePackages = "com.hq.hqusercenter")
//@ImportResource(locations = "classpath:/applicationContent.xml")
@MapperScan("com.hq.hqusercenter.mapper")
public class HquserCenterApplication {
    public static void main(String[] args) {
        SpringApplication.run(HquserCenterApplication.class, args);
    }

}
