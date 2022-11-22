package com.fandf.mongo.core.encoder;

import com.fandf.mongo.core.BaseEntity;
import com.fandf.mongo.core.access.InternalDao;
import com.fandf.mongo.core.annotations.Default;
import com.fandf.mongo.core.annotations.RefList;
import com.fandf.mongo.core.cache.DaoCache;
import com.fandf.mongo.core.utils.FieldUtil;
import com.fandf.mongo.core.utils.ReferenceUtil;

import java.lang.reflect.*;
import java.util.*;

@SuppressWarnings("unchecked")
public class RefListEncoder extends AbstractEncoder{
    
    private final RefList refList;
    private final boolean cascadeCU;
    
    public RefListEncoder(Object obj, Field field){
        super(obj, field);
        refList = field.getAnnotation(RefList.class);
        cascadeCU = refList.cascade().toUpperCase().indexOf(Default.CASCADE_CREATE)!=-1 || refList.cascade().toUpperCase().indexOf(Default.CASCADE_UPDATE)!=-1;
    }
    
    @Override
    public String getFieldName(){
        String fieldName = field.getName();
        String name = refList.name();
        if(!name.equals(Default.NAME)){
            fieldName = name;
        }
        return fieldName;
    }
    
    @Override
    public Object encode(){
        Object result = null;
        Class<?> type = field.getType();
        if(type.isArray()){
            result = encodeArray(type.getComponentType(), value);
        }else{
            ParameterizedType paramType = (ParameterizedType)field.getGenericType();
            Type[] types = paramType.getActualTypeArguments();
            int len = types.length;
            if(len == 1){
                result = encodeCollection((Class)types[0], value);
            }else if(len == 2){
                result = encodeMap();
            }
        }
        return result;
    }
    
    private Object encodeArray(Class type, Object val){
        Class<?> cls = FieldUtil.getRealType(type, field);
        InternalDao dao = DaoCache.getInstance().get(cls);
        int len = Array.getLength(val);
        List<Object> result = new ArrayList<>();
        for(int i=0; i<len; i++){
            BaseEntity entity = (BaseEntity)Array.get(val, i);
            if(entity != null){
                if(!withoutCascade && cascadeCU){
                    dao.saveWithoutCascade(entity, true);
                }
                result.add(ReferenceUtil.toDbReference(refList, entity.getClass(), entity.getId()));
            }
        }
        return result;
    }
    
    private Object encodeCollection(Class type, Object val){
        Collection<BaseEntity> collection = (Collection<BaseEntity>)val;
        List<Object> result = new ArrayList<>();
        Class<?> cls = FieldUtil.getRealType(type, field);
        InternalDao dao = DaoCache.getInstance().get(cls);
        for(BaseEntity entity : collection){
            if(entity != null){
                if(!withoutCascade && cascadeCU){
                    dao.saveWithoutCascade(entity, true);
                }
                result.add(ReferenceUtil.toDbReference(refList, entity.getClass(), entity.getId()));
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
        Class elementType = null;
        if(types[1] instanceof GenericArrayType){
            isArray = true;
            GenericArrayType g = (GenericArrayType)types[1];
            elementType = (Class)g.getGenericComponentType();
        }else if(types[1] instanceof ParameterizedType){
            isCollection = true;
            ParameterizedType p = (ParameterizedType)types[1];
            elementType = (Class)p.getActualTypeArguments()[0];
        }else{
            //in JDK8, type[1] of array, is a class, not array
            Class<?> actualType = FieldUtil.getClassOfType(types[1]);
            if(actualType.isArray()){
                isArray = true;
                elementType = actualType.getComponentType();
            }else{
                isSingle = true;
            }
        }
        //encode value by different type of V
        Map result = new HashMap();
        InternalDao dao = null;
        if(isSingle){
            Class<?> cls = FieldUtil.getRealType((Class)types[1], field);
            dao = DaoCache.getInstance().get(cls);
        }
        Map map = (Map)value;
        for(Object key : map.keySet()){
            Object entryValue = map.get(key);
            if(isSingle){
                BaseEntity entity = (BaseEntity)entryValue;
                if(entity != null){
                    if(!withoutCascade && cascadeCU){
                        dao.saveWithoutCascade(entity, true);
                    }
                    result.put(key, ReferenceUtil.toDbReference(refList, entity.getClass(), entity.getId()));
                }else{
                    result.put(key, null);
                }
            }
            else if(isArray){
                Object arr = encodeArray(elementType, entryValue);
                result.put(key, arr);
            }
            else if(isCollection){
                Object arr = encodeCollection(elementType, entryValue);
                result.put(key, arr);
            }
        }
        return result;
    }
    
}
