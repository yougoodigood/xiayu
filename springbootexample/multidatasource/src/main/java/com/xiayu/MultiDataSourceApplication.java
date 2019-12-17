package com.xiayu;

import io.shardingjdbc.spring.boot.SpringBootConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(exclude = SpringBootConfiguration.class)
public class MultiDataSourceApplication {
    public static void main(String[] args) {
        SpringApplication.run(MultiDataSourceApplication.class);
    }
}
