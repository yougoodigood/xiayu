package com.xiayu;

import org.apache.shardingsphere.shardingjdbc.spring.boot.SpringBootConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication(exclude = SpringBootConfiguration.class)
public class MultiDataSourceApplication {
    public static void main(String[] args) {
        SpringApplication.run(MultiDataSourceApplication.class);
    }
}
