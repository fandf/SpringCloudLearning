package com.fandf.demo;

import com.fandf.demo.design.抽象工厂.Test;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.support.GenericApplicationContext;

/**
 * @author fandongfeng
 * @date 2022/7/7 11:09
 */
@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder()
                .sources(DemoApplication.class)
                .initializers((ApplicationContextInitializer<GenericApplicationContext>) ctx -> {
                    System.out.println("在程序运行前向上下文注入Test");
                    ctx.registerBean("test", Test.class, Test::new);
                })
                .run(args);
    }


}
