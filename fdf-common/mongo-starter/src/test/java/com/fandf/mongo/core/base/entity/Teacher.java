package com.fandf.mongo.core.base.entity;

import com.fandf.mongo.core.annotations.EnsureIndex;
import com.fandf.mongo.core.annotations.Entity;
import com.fandf.mongo.core.annotations.Id;
import com.fandf.mongo.core.annotations.Property;

/**
 * @author fandongfeng
 * @date 2022/11/19 21:10
 */
@Entity
@EnsureIndex("{desc:text}")
public class Teacher {

    @Id
    private String id;
    private String name;
    @Property(lazy = true)
    private String desc;
    private Float price;


}
