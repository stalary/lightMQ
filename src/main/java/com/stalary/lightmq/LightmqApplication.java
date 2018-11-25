package com.stalary.lightmq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ServletComponentScan
@EnableScheduling
public class LightmqApplication {

    public static void main(String[] args) {
        SpringApplication.run(LightmqApplication.class, args);
    }
}
