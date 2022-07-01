package com.fandf.generator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author fandongfeng
 * @date 2022/6/30 10:43
 */
@EnableDiscoveryClient
@SpringBootApplication
public class CodeGeneratorApp {

    public static void main(String[] args) {
        SpringApplication.run(CodeGeneratorApp.class, args);
    }

}
