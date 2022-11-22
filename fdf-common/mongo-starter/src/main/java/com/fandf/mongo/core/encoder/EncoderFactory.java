package com.fandf.mongo.core.encoder;

import com.fandf.mongo.core.annotations.*;
import com.fandf.mongo.core.exception.ConstructorException;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public final class EncoderFactory {
    
    public static Encoder create(Object obj, Field field){
        Encoder encoder;
        if(field.getAnnotation(Id.class) != null){
            encoder = new IdEncoder(obj, field);
        }
        else if(field.getAnnotation(Embed.class) != null){
            encoder = new EmbedEncoder(obj, field);
        }
        else if(field.getAnnotation(EmbedList.class) != null){
            encoder = new EmbedListEncoder(obj, field);
        }
        else if(field.getAnnotation(Ref.class) != null){
            encoder = new RefEncoder(obj, field);
        }
        else if(field.getAnnotation(RefList.class) != null){
            encoder = new RefListEncoder(obj, field);
        }
        else if(field.getAnnotation(Ignore.class) != null){
            encoder = null;
        }
        else if(field.getAnnotation(CustomCodec.class) != null){
            encoder = createCustomEncoder(obj, field);
        }
        else{
            encoder = new PropertyEncoder(obj, field);  //no mapping annotation or @Property
        }
        return encoder;
    }
    
    private static Encoder createCustomEncoder(Object obj, Field field){
        CustomCodec codec = field.getAnnotation(CustomCodec.class);
        Class<?> clazz = codec.encoder();
        Constructor<?> cons = null;
        try{
            cons = clazz.getConstructor(Object.class, Field.class);
        } catch (NoSuchMethodException | SecurityException ex) {
            throw new ConstructorException(ex.getMessage());
        }
        if(cons == null){
            return null;
        }
        
        Object result = null;
        try{
            result = cons.newInstance(obj, field);
        } catch (IllegalAccessException | IllegalArgumentException | InstantiationException | InvocationTargetException ex) {
            throw new ConstructorException(ex.getMessage());
        } 
        if(result == null){
            return null;
        }
        
        return (Encoder)result;
    }
    
}
