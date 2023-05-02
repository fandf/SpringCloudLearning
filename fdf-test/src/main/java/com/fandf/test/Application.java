package com.fandf.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author fandongfeng
 * @date 2023/4/9 20:30
 */
@SpringBootApplication
@ComponentScan("com.fandf")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
