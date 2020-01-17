package com.xiayu;

import org.apache.shardingsphere.shardingjdbc.spring.boot.SpringBootConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(exclude = SpringBootConfiguration.class)
public class XiaYuApplication {
    public static void main(String[] args) {
        SpringApplication.run(XiaYuApplication.class);
    }
}
