package com.fandf.demo.propertyeditor;

import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.ConditionalGenericConverter;
import org.springframework.core.convert.support.DefaultConversionService;

import java.util.Collections;
import java.util.Set;

/**
 * @author fandongfeng
 */
public class StringToPersonConverter implements ConditionalGenericConverter {
    @Override
    public boolean matches(TypeDescriptor sourceType, TypeDescriptor targetType) {
        return sourceType.getType().equals(String.class) && targetType.getType().equals(Person.class);
    }

    @Override
    public Set<ConvertiblePair> getConvertibleTypes() {
        return Collections.singleton(new ConvertiblePair(String.class, Person.class));
    }

    @Override
    public Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
        Person person = new Person();
        String[] split = String.valueOf(source).split(",");
        person.setId(Long.valueOf(split[0]));
        person.setName(split[1]);
        person.setAge(Integer.valueOf(split[2]));
        return person;
    }

    public static void main(String[] args) {
        DefaultConversionService conversionService = new DefaultConversionService();
        conversionService.addConverter(new StringToPersonConverter());
        Person value = conversionService.convert("1,张三,15", Person.class);
        System.out.println(value);
    }

}
