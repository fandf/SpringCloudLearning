package com.fandf.mongo.core.decoder;

import com.fandf.mongo.core.annotations.*;
import com.fandf.mongo.core.exception.ConstructorException;
import com.mongodb.DBObject;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;


public final class DecoderFactory {
    
    public static Decoder create(Field field, DBObject dbo){
        Decoder decoder;
        if(field.getAnnotation(Id.class) != null){
            decoder = new IdDecoder(field, dbo);
        }
        else if(field.getAnnotation(Embed.class) != null){
            decoder = new EmbedDecoder(field, dbo);
        }
        else if(field.getAnnotation(EmbedList.class) != null){
            decoder = new EmbedListDecoder(field, dbo);
        }
        else if(field.getAnnotation(Ref.class) != null){
            decoder = new RefDecoder(field, dbo);
        }
        else if(field.getAnnotation(RefList.class) != null){
            decoder = new RefListDecoder(field, dbo);
        }
        else if(field.getAnnotation(Ignore.class) != null){
            decoder = null;
        }
        else if(field.getAnnotation(CustomCodec.class) != null){
            decoder = createCustomDecoder(field, dbo);
        }
        else{
            decoder = new PropertyDecoder(field, dbo);
        }
        return decoder;
    }
    
    private static Decoder createCustomDecoder(Field field, DBObject dbo){
        CustomCodec codec = field.getAnnotation(CustomCodec.class);
        Class<?> clazz = codec.decoder();
        Constructor<?> cons = null;
        try{
            cons = clazz.getConstructor(Field.class, DBObject.class);
        } catch (NoSuchMethodException | SecurityException ex) {
            throw new ConstructorException(ex.getMessage());
        }
        if(cons == null){
            return null;
        }
        
        Object result = null;
        try{
            result = cons.newInstance(field, dbo);
        } catch (IllegalAccessException | IllegalArgumentException | InstantiationException | InvocationTargetException ex) {
            throw new ConstructorException(ex.getMessage());
        } 
        if(result == null){
            return null;
        }
        
        return (Decoder)result;
    }
    
}
