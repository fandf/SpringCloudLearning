package com.fandf.mongo.core.encoder;

import com.fandf.mongo.core.annotations.Default;
import com.fandf.mongo.core.annotations.EmbedList;
import com.fandf.mongo.core.utils.FieldUtil;
import com.fandf.mongo.core.utils.MapperUtil;
import com.mongodb.DBObject;

import java.lang.reflect.*;
import java.util.*;

@SuppressWarnings("unchecked")
public class EmbedListEncoder extends AbstractEncoder{
    
    public EmbedListEncoder(Object obj, Field field){
        super(obj, field);
    }

    @Override
    public String getFieldName() {
        String fieldName = field.getName();
        EmbedList embedList = field.getAnnotation(EmbedList.class);
        String name = embedList.name();
        if(!name.equals(Default.NAME)){
            fieldName = name;
        }
        return fieldName;
    }

    @Override
    public Object encode() {
        Object result = null;
        Class<?> type = field.getType();
        if(type.isArray()){
            Class<?> comType = type.getComponentType();
            if(comType.isEnum()){
                result = encodeEnumArray(value);
            }else{
                result = encodeArray(value);
            }
        }else{
            ParameterizedType paramType = (ParameterizedType)field.getGenericType();
            Type[] types = paramType.getActualTypeArguments();
            int len = types.length;
            if(len == 1){
                Class<?> realCls = (Class)types[0];
                if(realCls.isEnum()){
                    result = encodeEnumCollection(value);
                }else{
                    result = encodeCollection(value);
                }
            }else if(len == 2){
                result = encodeMap();
            }
        }
        return result;
    }
    
    private Object encodeEnumArray(Object arr){
        int len = Array.getLength(arr);
        List<String> result = new ArrayList<>();
        for(int i=0; i<len; i++){
            Object o = Array.get(arr, i);
            if(o != null){
                result.add(o.toString());
            }
        }
        return result;
    }
    
    private Object encodeArray(Object arr){
        int len = Array.getLength(arr);
        List<DBObject> result = new ArrayList<>();
        for(int i=0; i<len; i++){
            Object o = Array.get(arr, i);
            if(o != null){
                result.add(MapperUtil.toDBObject(o));
            }
        }
        return result;
    }
    
    private Object encodeEnumCollection(Object coll){
        List<String> result = new ArrayList<>();
        Collection collection = (Collection)coll;
        for(Object o : collection){
            if(o != null){
                result.add(o.toString());
            }
        }
        return result;
    }
    
    private Object encodeCollection(Object coll){
        List<DBObject> result = new ArrayList<>();
        Collection collection = (Collection)coll;
        for(Object o : collection){
            if(o != null){
                result.add(MapperUtil.toDBObject(o));
            }
        }
        return result;
    }
    
    private Object encodeMap(){
        //for Map<K,V>, first to check the type of V
        ParameterizedType paramType = (ParameterizedType)field.getGenericType();
        Type[] types = paramType.getActualTypeArguments();
        boolean isArray = false;
        boolean isCollection = false;
        boolean isSingle = false;
        if(types[1] instanceof GenericArrayType){
            isArray = true;
        }else if(types[1] instanceof ParameterizedType){
            isCollection = true;
        }else{
            //in JDK8, type[1] of array, is a class, not array
            Class<?> actualType = FieldUtil.getClassOfType(types[1]);
            if(actualType.isArray()){
                isArray = true;
            }else{
                isSingle = true;
            }
        }
        //encode value by different type of V
        Map map = (Map)value;
        Map result = new HashMap();
        for(Object key : map.keySet()){
            Object entryValue = map.get(key);
            if(entryValue == null){
                result.put(key, null);
                continue;
            }
            if(isSingle){
                result.put(key, MapperUtil.toDBObject(entryValue));
            }else if(isArray){
                result.put(key, encodeArray(entryValue));
            }else if(isCollection){
                result.put(key, encodeCollection(entryValue));
            }
        }
        return result;
    }
    
}
