package com.fandf.mongo.core.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Entity {
    public String name() default Default.NAME;

    public String connection() default Default.NAME;

    public SplitType split() default SplitType.NONE;

    public boolean capped() default false;

    public long capSize() default Default.CAP_SIZE;

    public long capMax() default Default.CAP_MAX;
}
