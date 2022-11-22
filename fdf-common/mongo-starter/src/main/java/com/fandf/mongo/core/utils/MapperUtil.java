package com.fandf.mongo.core.utils;

import com.fandf.mongo.core.annotations.Default;
import com.fandf.mongo.core.annotations.Entity;
import com.fandf.mongo.core.cache.ConstructorCache;
import com.fandf.mongo.core.cache.FieldsCache;
import com.fandf.mongo.core.decoder.Decoder;
import com.fandf.mongo.core.decoder.DecoderFactory;
import com.fandf.mongo.core.encoder.Encoder;
import com.fandf.mongo.core.encoder.EncoderFactory;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public final class MapperUtil {
    
    /**
     * Convert a DBObject to entity object
     * @param <T>
     * @param clazz
     * @param dbo
     * @return 
     */
    public static <T> T fromDBObject(Class<T> clazz, DBObject dbo){
        return fromDBObject(clazz, dbo, false);
    }
    
    public static <T> T fromDBObject(Class<T> clazz, DBObject dbo, boolean withoutCascade){
        if(dbo == null){
            return null;
        }
        T obj = ConstructorCache.getInstance().create(clazz);
        Field[] fields = FieldsCache.getInstance().get(clazz);
        for(Field field : fields){
            Decoder decoder = DecoderFactory.create(field, dbo);
            if(decoder!=null && !decoder.isNullField()){
                decoder.setWithoutCascade(withoutCascade);
                decoder.decode(obj);
            }
        }
        return obj;
    }
    
    
    /**
     * Convert an entity object to DBObject
     * @param obj
     * @return 
     */
    public static DBObject toDBObject(Object obj){
        return toDBObject(obj, false);
    }
    
    public static DBObject toDBObject(Object obj, boolean withoutCascade){
        if(obj == null){
            return null;
        }
        Class<?> clazz = obj.getClass();
        Field[] fields = FieldsCache.getInstance().get(clazz);
        DBObject dbo = new BasicDBObject();
        for(Field field : fields){
            Encoder encoder = EncoderFactory.create(obj, field);
            if(encoder!=null && !encoder.isNullField()){
                encoder.setWithoutCascade(withoutCascade);
                dbo.put(encoder.getFieldName(), encoder.encode());
            }
        }
        return dbo;
    }
    
    public static <T> List<T> toList(Class<T> clazz, DBCursor cursor){
        return toList(clazz, cursor, false);
    }
    
    public static <T> List<T> toList(Class<T> clazz, DBCursor cursor, boolean withoutCascade){
        List<T> list = new ArrayList<>();
        try {
            while(cursor.hasNext()){
                DBObject dbo = cursor.next();
                list.add(fromDBObject(clazz, dbo, withoutCascade));
            }
        } finally {
            cursor.close();
        }
        return list;
    }
    
    /**
     * Get the name property of @Entity annotation. 
     * If the name property is not set, then return the class' name, in lower case type.
     * @param clazz
     * @return 
     */
    public static String getEntityName(Class<?> clazz){
        Entity entity = clazz.getAnnotation(Entity.class);
        String name = entity.name();
        if(name.equals(Default.NAME)){
            name = clazz.getSimpleName().toLowerCase();
        }
        return name;
    }
    
}
