package com.fandf.mongo.core.decoder;

import com.fandf.mongo.core.BaseEntity;
import com.fandf.mongo.core.BaseQuery;
import com.fandf.mongo.core.access.InternalDao;
import com.fandf.mongo.core.annotations.Default;
import com.fandf.mongo.core.annotations.RefList;
import com.fandf.mongo.core.cache.ConstructorCache;
import com.fandf.mongo.core.cache.DaoCache;
import com.fandf.mongo.core.utils.DataType;
import com.fandf.mongo.core.utils.FieldUtil;
import com.fandf.mongo.core.utils.Operator;
import com.fandf.mongo.core.utils.ReferenceUtil;
import com.mongodb.DBObject;

import java.lang.reflect.*;
import java.util.*;

@SuppressWarnings("unchecked")
public class RefListDecoder extends AbstractDecoder{
    
    private final RefList refList;
    
    public RefListDecoder(Field field, DBObject dbo){
        super(field);
        refList = field.getAnnotation(RefList.class);
        String fieldName = field.getName();
        String name = refList.name();
        if(!name.equals(Default.NAME)){
            fieldName = name;
        }
        value = dbo.get(fieldName);
    }
    
    @Override
    public void decode(Object obj){
        Class<?> type = field.getType();
        if(type.isArray()){
            Object arr = decodeArray(value, type.getComponentType());
            FieldUtil.set(obj, field, arr);
        }else{
            ParameterizedType paramType = (ParameterizedType)field.getGenericType();
            Type[] types = paramType.getActualTypeArguments();
            int len = types.length;
            if(len == 1){
                List list = decodeCollection(value, (Class)types[0]);
                if(DataType.isListType(type) || DataType.isCollectionType(type)){
                    FieldUtil.set(obj, field, list);
                }
                else if(DataType.isSetType(type)){
                    FieldUtil.set(obj, field, new HashSet(list));
                }
                else if(DataType.isQueueType(type)){
                    FieldUtil.set(obj, field, new LinkedList(list));
                }
            }else if(len == 2){
                Object map = decodeMap();
                FieldUtil.set(obj, field, map);
            }
        }
    }
    
    private Object decodeArray(Object val, Class elementClass){
        elementClass = FieldUtil.getRealType(elementClass, field);
        List list = (ArrayList)val;
        if(list.isEmpty()){
            return null;
        }
        int size = list.size();
        Object arr = Array.newInstance(elementClass, size);
        //not cascade read
        if(refList.cascade().toUpperCase().indexOf(Default.CASCADE_READ)==-1 || withoutCascade){
            for(int i=0; i<size; i++){
                Object item = list.get(i);
                if(item != null){
                    String refId = ReferenceUtil.fromDbReference(refList, item);
                    BaseEntity refObj = (BaseEntity) ConstructorCache.getInstance().create(elementClass);
                    refObj.setId(refId);
                    Array.set(arr, i, refObj);
                }else{
                    Array.set(arr, i, null);
                }
            }
        }
        //cascade read
        else {
            List<String> idList = new ArrayList<>();
            for(int i=0; i<size; i++){
                Object item = list.get(i);
                if(item != null){
                    String refId = ReferenceUtil.fromDbReference(refList, item);
                    idList.add(refId);
                }
            }
            InternalDao dao = DaoCache.getInstance().get(elementClass);
            BaseQuery query = dao.query().in(Operator.ID, idList);
            query.setWithoutCascade(true);
            String sort = refList.sort();
            if(!sort.equals(Default.SORT)){
                query.sort(sort);
            }
            List<BaseEntity> entityList = query.results();
            //when query returns, the size maybe changed
            if(entityList.size() != size){
                size = entityList.size();
                arr = Array.newInstance(elementClass, size);
            }
            for(int i=0; i<size; i++){
                Array.set(arr, i, entityList.get(i));
            }
        }
        return arr;
    }
    
    private List decodeCollection(Object val, Class elementClass){
        elementClass = FieldUtil.getRealType(elementClass, field);
        Collection collection = (Collection)val;
        if(collection.isEmpty()){
            return null;
        }
        List<BaseEntity> result = new ArrayList<>();
        //not cascade read
        if(refList.cascade().toUpperCase().indexOf(Default.CASCADE_READ)==-1 || withoutCascade){
            for(Object item : collection){
                if(item != null){
                    String refId = ReferenceUtil.fromDbReference(refList, item);
                    BaseEntity refObj = (BaseEntity)ConstructorCache.getInstance().create(elementClass);
                    refObj.setId(refId);
                    result.add(refObj);
                }
            }
        }
        //cascade read
        else {
            List<String> idList = new ArrayList<>();
            for(Object item : collection){
                if(item != null){
                    String refId = ReferenceUtil.fromDbReference(refList, item);
                    idList.add(refId);
                }
            }
            InternalDao dao = DaoCache.getInstance().get(elementClass);
            BaseQuery query = dao.query().in(Operator.ID, idList);
            query.setWithoutCascade(true);
            String sort = refList.sort();
            if(!sort.equals(Default.SORT)){
                query.sort(sort);
            }
            result = query.results();
        }
        return result;
    }
    
    private Map decodeMap(){
        Map map = (Map)value;
        if(map.isEmpty()){
            return null;
        }
        
        //for Map<K,V>, first to check the type of V
        ParameterizedType paramType = (ParameterizedType)field.getGenericType();
        Type[] types = paramType.getActualTypeArguments();
        //3 different types of V
        boolean isArray = false;
        boolean isCollection = false;
        boolean isSingle = false;
        Class vType = null;
        Class elementType = null;
        //in JDK6, type[1] of array, is instanceof GenericArrayType
        if(types[1] instanceof GenericArrayType){
            isArray = true;
            GenericArrayType g = (GenericArrayType)types[1];
            elementType = (Class)g.getGenericComponentType();
        }else if(types[1] instanceof ParameterizedType){
            isCollection = true;
            ParameterizedType p = (ParameterizedType)types[1];
            vType = (Class)p.getRawType();
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
        
        //decode value by different type of V
        Map result = new HashMap();
        boolean cascadeRead = false;
        Class<?> cls  = null;
        InternalDao dao = null;
        if(isSingle){
            cls  = FieldUtil.getRealType((Class)types[1], field);
            cascadeRead = (refList.cascade().toUpperCase().indexOf(Default.CASCADE_READ) != -1);
            if(!withoutCascade && cascadeRead){
                dao = DaoCache.getInstance().get(cls);
            }
        }
        for(Object key : map.keySet()){
            Object entryValue = map.get(key);
            if(entryValue == null){
                result.put(key, null);
                continue;
            }
            if(isSingle){
                String refId = ReferenceUtil.fromDbReference(refList, entryValue);
                BaseEntity refObj = null;
                if(!withoutCascade && cascadeRead){
                    refObj = (BaseEntity)dao.findOneLazily(refId, true);
                }else{
                    refObj = (BaseEntity)ConstructorCache.getInstance().create(cls);
                    refObj.setId(refId);
                }
                result.put(key, refObj);
            }else if(isArray){
                Object arr = decodeArray(entryValue, elementType);
                result.put(key, arr);
            }else if(isCollection){
                List list = decodeCollection(entryValue, elementType);
                if(DataType.isListType(vType) || DataType.isCollectionType(vType)){
                    result.put(key, list);
                }
                else if(DataType.isSetType(vType)){
                    result.put(key, new HashSet(list));
                }
                else if(DataType.isQueueType(vType)){
                    result.put(key, new LinkedList(list));
                }
            }
        }
        return result;
    }
    
}
