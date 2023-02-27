package com.fandf.demo.propertyeditor;

import lombok.Data;

/**
 * 在Spring源码中，有可能需要把String转成其他类型，所以在Spring源码中提供了一些技术来更方便的做对象的类型转化
 *
 * @author fandongfeng
 */
@Data
public class Person {

    private Long id;
    private String name;
    private Integer age;

}
