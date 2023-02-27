package com.fandf.demo.propertyeditor;

import org.springframework.beans.SimpleTypeConverter;

/**
 * @author fandongfeng
 */
public class StringToPersonTypeConverter {


    public static void main(String[] args) {
        SimpleTypeConverter typeConverter = new SimpleTypeConverter();
        typeConverter.registerCustomEditor(Person.class, new StringToPersonPropertyEditor());
        Person value = typeConverter.convertIfNecessary("1,张三,16", Person.class);
        System.out.println(value);
    }

}
