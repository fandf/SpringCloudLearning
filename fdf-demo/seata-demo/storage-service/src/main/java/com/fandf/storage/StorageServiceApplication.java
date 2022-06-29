package com.fandf.storage;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author fandongfeng
 * @date 2022/6/28 14:38
 */
@EnableDiscoveryClient
@EnableFeignClients
@MapperScan({"com.fandf.storage.mapper"})
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class StorageServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(StorageServiceApplication.class, args);
    }

}
