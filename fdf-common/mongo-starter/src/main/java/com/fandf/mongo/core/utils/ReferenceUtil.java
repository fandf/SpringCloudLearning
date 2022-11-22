package com.fandf.mongo.core.utils;

import com.fandf.mongo.core.annotations.Id;
import com.fandf.mongo.core.annotations.Ref;
import com.fandf.mongo.core.annotations.RefList;
import com.fandf.mongo.core.cache.FieldsCache;
import com.mongodb.DBRef;
import org.bson.types.ObjectId;

import java.lang.reflect.Field;

public final class ReferenceUtil {
    
    public static Object toDbReference(Ref ref, Class<?> clazz, String idStr){
        if(StringUtil.isBlank(idStr)){
            return null;
        }
        Object result;
        if(ref.reduced()){
            result = toManualRef(clazz, idStr);
        }else{
            result = toDBRef(clazz, idStr);
        }
        return result;
    }
    
    public static Object toDbReference(RefList refList, Class<?> clazz, String idStr){
        if(StringUtil.isBlank(idStr)){
            return null;
        }
        Object result;
        if(refList.reduced()){
            result = toManualRef(clazz, idStr);
        }else{
            result = toDBRef(clazz, idStr);
        }
        return result;
    }
    
    private static Object toManualRef(Class<?> clazz, String idStr){
        Object result = null;
        Field idField = FieldsCache.getInstance().getIdField(clazz);
        Id idAnnotation = idField.getAnnotation(Id.class);
        switch(idAnnotation.type()){
            case AUTO_GENERATE:
                result = new ObjectId(idStr);
                break;
            case AUTO_INCREASE:
                result = Long.parseLong(idStr);
                break;
            case USER_DEFINE:
                result = idStr;
                break;
        }
        return result;
    }
    
    private static DBRef toDBRef(Class<?> clazz, String idStr){
        String name = MapperUtil.getEntityName(clazz);
        Object dbId = IdUtil.toDbId(clazz, idStr);
        return new DBRef(name, dbId);
    }
    
    public static String fromDbReference(Ref ref, Object value){
        String result;
        if(ref.reduced()){
            result = value.toString();
        }else{
            DBRef dbRef = (DBRef)value;
            result = dbRef.getId().toString();
        }
        return result;
    }
    
    public static String fromDbReference(RefList refList, Object value){
        String result;
        if(refList.reduced()){
            result = value.toString();
        }else{
            DBRef dbRef = (DBRef)value;
            result = dbRef.getId().toString();
        }
        return result;
    }
    
    public static Object toDbReference(Class<?> clazz, String fieldName, Class<?> refClass, String idStr){
        Object result;
        Field refField = FieldsCache.getInstance().getField(clazz, fieldName);
        Ref ref = refField.getAnnotation(Ref.class);
        if(ref != null){
            result = toDbReference(ref, refClass, idStr);
        }else{
            RefList refList = refField.getAnnotation(RefList.class);
            result = toDbReference(refList, refClass, idStr);
        }
        return result;
    }

}
