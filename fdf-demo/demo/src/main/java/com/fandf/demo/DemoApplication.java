package com.fandf.demo;

import com.fandf.demo.design.抽象工厂.Test;
import com.fandf.demo.propertyeditor.Person;
import com.fandf.demo.propertyeditor.StringToPersonConverter;
import com.fandf.demo.propertyeditor.StringToPersonPropertyEditor;
import org.springframework.beans.factory.config.CustomEditorConfigurer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.context.support.GenericApplicationContext;

import java.beans.PropertyEditor;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author fandongfeng
 * @date 2022/7/7 11:09
 */
@SpringBootApplication
public class DemoApplication {

    @Bean
    public ConversionServiceFactoryBean conversionService() {
        ConversionServiceFactoryBean conversionServiceFactoryBean = new ConversionServiceFactoryBean();
        conversionServiceFactoryBean.setConverters(Collections.singleton(new StringToPersonConverter()));
        return conversionServiceFactoryBean;
    }



    @Bean
    public CustomEditorConfigurer customEditorConfigurer() {
        CustomEditorConfigurer customEditorConfigurer = new CustomEditorConfigurer();
        Map<Class<?>, Class<? extends PropertyEditor>> propertyEditorMap = new HashMap<>();
        // 表示StringToPersonPropertyEditor可以将String转化成Person类型，在Spring源码中，如果发现当前对象是String，而需要的类型是Person，就会使用该PropertyEditor来做类型转化
        propertyEditorMap.put(Person.class, StringToPersonPropertyEditor.class);
        customEditorConfigurer.setCustomEditors(propertyEditorMap);
        return customEditorConfigurer;
    }

    public static void main(String[] args) {
//        new SpringApplicationBuilder()
//                .sources(DemoApplication.class)
//                .initializers((ApplicationContextInitializer<GenericApplicationContext>) ctx -> {
//                    System.out.println("在程序运行前向上下文注入Test");
//                    ctx.registerBean("customEditorConfigurer", CustomEditorConfigurer.class);
//                })
//                .run(args);
        SpringApplication.run(DemoApplication.class, args);
    }


}
