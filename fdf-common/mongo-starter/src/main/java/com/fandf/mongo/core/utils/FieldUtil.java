package com.fandf.mongo.core.utils;

import com.fandf.mongo.core.annotations.*;
import com.fandf.mongo.core.cache.FieldsCache;
import com.fandf.mongo.core.exception.BaseException;
import com.fandf.mongo.core.exception.FieldException;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

import java.lang.reflect.Field;
import java.lang.reflect.Type;


public final class FieldUtil {
    
    //Note: there is an blank space at the end.
    private static final String TYPE_NAME_PREFIX = "class ";
    
    public static Object get(Object obj, Field f){
        Object value = null;
        try {
            value = f.get(obj);
        } catch (IllegalAccessException | IllegalArgumentException ex) {
            throw new FieldException(ex.getMessage());
        }
        return value;
    }
    
    public static void set(Object obj, Field f, Object value){
        try{
            f.set(obj, value);
        }catch(IllegalAccessException | IllegalArgumentException ex){
            throw new FieldException(ex.getMessage());
        }
    }
    
    /**
     * Copy an object's properties to another object. 
     * <p>Note: The source and target object can't be null.</p>
     * @param src
     * @param target 
     */
    public static void copy(Object src, Object target){
        if(src==null || target==null){
            return;
        }
        Field[] fields = FieldsCache.getInstance().get(src.getClass());
        for(Field f : fields){
            Object value = get(src, f);
            set(target, f, value);
        }
    }
    
    public static Class<?> getRealType(Field field){
        Class<?> clazz = field.getType();
        return getRealType(clazz, field);
    }
    
    public static Class<?> getRealType(Class<?> clazz, Field field){
        Class<?> cls = clazz;
        if(clazz.isInterface()){
            Ref ref = field.getAnnotation(Ref.class);
            if(ref!=null && ref.impl()!= Default.class){
                cls = ref.impl();
            }
            else{
                RefList refList = field.getAnnotation(RefList.class);
                if(refList!=null && refList.impl()!=Default.class){
                    cls = refList.impl();
                }
            }
        }
        return cls;
    }
    
    public static Class<?> getClassOfType(Type type) {
        if(type == null) {
            return null;
        }
        String className = type.toString();
        if (className.startsWith(TYPE_NAME_PREFIX)) {
            className = className.substring(TYPE_NAME_PREFIX.length());
        }
        Class<?> cls = null;
        try{
            cls = Class.forName(className);
        }catch(ClassNotFoundException ex){
            throw new BaseException(ex.getMessage());
        }
        return cls;
    }
    
    public static DBObject getLazyFields(Class<?> clazz){
        DBObject lazyKeys = new BasicDBObject();
        Field[] fields = FieldsCache.getInstance().get(clazz);
        for(Field field : fields){
            String fieldName = field.getName();
            Property property = field.getAnnotation(Property.class);
            if(property!=null && property.lazy()){
                String name = property.name();
                if(!name.equals(Default.NAME)){
                    fieldName = name;
                }
                lazyKeys.put(fieldName, 0);
                continue;
            }
            Embed embed = field.getAnnotation(Embed.class);
            if(embed!=null && embed.lazy()){
                String name = embed.name();
                if(!name.equals(Default.NAME)){
                    fieldName = name;
                }
                lazyKeys.put(fieldName, 0);
                continue;
            }
            EmbedList embedList = field.getAnnotation(EmbedList.class);
            if(embedList!=null && embedList.lazy()){
                String name = embedList.name();
                if(!name.equals(Default.NAME)){
                    fieldName = name;
                }
                lazyKeys.put(fieldName, 0);
                continue;
            }
        }
        return lazyKeys;
    }
    
    public static boolean hasCascadeDelete(Class<?> clazz){
        boolean result = false;
        Field[] fields = FieldsCache.getInstance().get(clazz);
        for(Field f : fields){
            Ref ref = f.getAnnotation(Ref.class);
            if(ref!=null && ref.cascade().toUpperCase().indexOf(Default.CASCADE_DELETE)!=-1){
                result = true;
                break;
            }
            RefList refList = f.getAnnotation(RefList.class);
            if(refList!=null && refList.cascade().toUpperCase().indexOf(Default.CASCADE_DELETE)!=-1){
                result = true;
                break;
            }
        }
        return result;
    }
    
}
