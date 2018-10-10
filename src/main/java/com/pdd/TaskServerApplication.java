package com.pdd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
public class TaskServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(TaskServerApplication.class, args);
    }
}
