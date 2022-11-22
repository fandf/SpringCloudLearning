
package com.fandf.mongo.core.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RefList {
    public String name() default Default.NAME;

    public String cascade() default Default.CASCADE;

    public String sort() default Default.SORT;

    public boolean reduced() default false;

    public Class<?> impl() default Default.class;
}