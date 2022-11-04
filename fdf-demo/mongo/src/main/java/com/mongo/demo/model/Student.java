package com.mongo.demo.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

/**
 * @author : leilei
 * @date : 10:23 2020/2/16
 * @desc :student 实体类
 */
@Data
@Document(collection = "student")  //指定要对应的文档名(表名）
public class Student {

    /*** 自定义mongo主键 加此注解可自定义主键类型以及自定义自增规则
     *  若不加 插入数据数会默认生成 ObjectId 类型的_id 字段
     *  org.springframework.data.annotation.Id 包下
     *  mongo库主键字段还是为_id (本文实体类中字段为为id，意思查询字段为_id，但查询结果_id会映射到实体对象id字段中）
     */
    @Id
    private Long id;
    private String username;
    private LocalDateTime timer;
}

