package com.codev.guide;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.codev.guide.mapper")
public class CodevDynamicsGuideStartFrontApplication {

    public static void main(String[] args) {
        SpringApplication.run(CodevDynamicsGuideStartFrontApplication.class, args);
    }

}
