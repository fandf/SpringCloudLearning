package com.fandf.mongo.core.encoder;

import com.fandf.mongo.core.BaseEntity;
import com.fandf.mongo.core.annotations.Default;
import com.fandf.mongo.core.annotations.Embed;
import com.fandf.mongo.core.exception.AnnotationException;
import com.fandf.mongo.core.utils.MapperUtil;

import java.lang.reflect.Field;


public class EmbedEncoder extends AbstractEncoder {
    
    public EmbedEncoder(Object obj, Field field){
        super(obj, field);
    }
    
    @Override
    public String getFieldName(){
        String fieldName = field.getName();
        Embed embed = field.getAnnotation(Embed.class);
        String name = embed.name();
        if(!name.equals(Default.NAME)){
            fieldName = name;
        }
        return fieldName;
    }
    
    @Override
    public Object encode(){
        Class<?> type = field.getType();
        if(type.isEnum()){
            return value.toString();
        }
        
        //tip for wrong use of @Embed
        if(value instanceof BaseEntity){
            throw new AnnotationException("The Embed object should not be BuguEntity!");
        }
        
        return MapperUtil.toDBObject(value);
    }
    
}
