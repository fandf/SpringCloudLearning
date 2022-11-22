package com.fandf.mongo.core;

import com.fandf.mongo.core.access.InternalDao;
import com.fandf.mongo.core.annotations.Default;
import com.fandf.mongo.core.annotations.Ref;
import com.fandf.mongo.core.annotations.RefList;
import com.fandf.mongo.core.cache.DaoCache;
import com.fandf.mongo.core.cache.FieldsCache;
import com.fandf.mongo.core.utils.DataType;
import com.fandf.mongo.core.utils.FieldUtil;
import com.fandf.mongo.core.utils.MapperUtil;
import com.fandf.mongo.core.utils.Operator;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

import java.lang.reflect.*;
import java.util.*;
import java.util.Map.Entry;

@SuppressWarnings("unchecked")
public final class BaseMapper {
    
    /**
     * Convert to JSON string.
     * @param obj
     * @return 
     */
    public static String toJsonString(Object obj){
        if(obj == null){
            return null;
        }
        BasicDBObject bdbo;
        if(obj instanceof DBObject){
            bdbo = (BasicDBObject)obj;
        }else{
            DBObject dbo = MapperUtil.toDBObject(obj, true);
            bdbo = (BasicDBObject)dbo;
        }
        return bdbo.toString();
    }
    
    /**
     * Fetch out the lazy @Property, @Embed, @EmbedList field of a list
     * @param list the list needs to operate on
     */
    public static void fetchLazy(List list){
        for(Object o : list){
            if(o != null){
                BaseEntity obj = (BaseEntity)o;
                BaseEntity newObj = (BaseEntity) DaoCache.getInstance().get(obj.getClass()).findOne(obj.getId());
                FieldUtil.copy(newObj, obj);
            }
        }
    }
    
    /**
     * Fetch out the lazy @Property, @Embed, @EmbedList field of an entity.
     * <p>The entity must be an element of a list.</p>
     * @param obj the entity needs to operate on
     */
    public static void fetchLazy(BaseEntity obj){
        BaseEntity newObj = (BaseEntity)DaoCache.getInstance().get(obj.getClass()).findOne(obj.getId());
        FieldUtil.copy(newObj, obj);
    }
    
    /**
     * Fetch out the cascade @Ref or @RefList entity.
     * @param obj the entity needs to operate on
     * @param names the fields' names
     */
    public static void fetchCascade(BaseEntity obj, String... names){
        if(obj != null){
            for(String name : names){
                String remainder = null;
                int index = name.indexOf(".");
                if(index > 0){
                    remainder = name.substring(index+1);
                    name = name.substring(0, index);
                }
                fetchOneLevel(obj, name);
                if(remainder != null){
                    fetchRemainder(obj, name, remainder);
                }
            }
        }
    }
    
    /**
     * Fetch out the cascade @Ref or @RefList entity.
     * @param list the list needs to operate on
     * @param names the fields' names
     */
    public static void fetchCascade(List list, String... names){
        for(Object o : list){
            if(o != null){
                BaseEntity obj = (BaseEntity)o;
                fetchCascade(obj, names);
            }
        }
    }
    
    private static void fetchOneLevel(BaseEntity obj, String fieldName){
        Field field = FieldsCache.getInstance().getField(obj.getClass(), fieldName);
        if(field.getAnnotation(Ref.class) != null){
            fetchRef(obj, field);
        }else if(field.getAnnotation(RefList.class) != null){
            fetchRefList(obj, field);
        }
    }
    
    private static void fetchRemainder(BaseEntity obj, String fieldName, String remainder){
        Field field = FieldsCache.getInstance().getField(obj.getClass(), fieldName);
        Object value = FieldUtil.get(obj, field);
        if(value == null){
            return;
        }
        if(field.getAnnotation(Ref.class) != null){
            BaseEntity entity = (BaseEntity)value;
            fetchCascade(entity, remainder);
        }else if(field.getAnnotation(RefList.class) != null){
            Class type = field.getType();
            if(DataType.isMapType(type)){
                //To-do:
                //this is not strictly correct. It won't works in some situation
                Map<Object, BaseEntity> map = (Map<Object, BaseEntity>)value;
                for(Entry<Object, BaseEntity> entry : map.entrySet()){
                    fetchCascade(entry.getValue(), remainder);
                }
            }
            else{
                Collection<BaseEntity> collection = (Collection<BaseEntity>)value;
                for(BaseEntity entity : collection){
                    fetchCascade(entity, remainder);
                }
            }
        }
    }
    
    private static void fetchRef(BaseEntity obj, Field field){
        Object val = FieldUtil.get(obj, field);
        if( val == null){
            return;
        }
        BaseEntity refObj = (BaseEntity)val;
        String id = refObj.getId();
        Class cls = FieldUtil.getRealType(field);
        InternalDao dao = DaoCache.getInstance().get(cls);
        Object value = dao.findOne(id);
        FieldUtil.set(obj, field, value);
    }
    
    private static void fetchRefList(BaseEntity obj, Field field){
        Object val = FieldUtil.get(obj, field);
        if(val == null){
            return;
        }
        Class<?> type = field.getType();
        if(type.isArray()){
            Object arr = fetchArrayValue(val, field, type.getComponentType());
            FieldUtil.set(obj, field, arr);
        }else{
            ParameterizedType paramType = (ParameterizedType)field.getGenericType();
            Type[] types = paramType.getActualTypeArguments();
            int len = types.length;
            if(len == 1){
                //for Collection
                List list = fetchCollectionValue(val, field, (Class)types[0]);
                if(DataType.isListType(type)){
                    FieldUtil.set(obj, field, list);
                }
                else if(DataType.isSetType(type)){
                    FieldUtil.set(obj, field, new HashSet(list));
                }
                else if(DataType.isQueueType(type)){
                    FieldUtil.set(obj, field, new LinkedList(list));
                }
            }else if(len == 2){
                //for Map
                Map map = fetchMapValue(val, field);
                FieldUtil.set(obj, field, map);
            }
        }
    }
    
    private static Object fetchArrayValue(Object val, Field field, Class elementClass) {
        int len = Array.getLength(val);
        if(len == 0){
            return null;
        }
        elementClass = FieldUtil.getRealType(elementClass, field);
        Object arr = Array.newInstance(elementClass, len);
        List<String> idList = new ArrayList<>();
        for(int i=0; i<len; i++){
            Object item = Array.get(val, i);
            if(item != null){
                BaseEntity ent = (BaseEntity)item;
                idList.add(ent.getId());
            }
        }
        RefList refList = field.getAnnotation(RefList.class);
        String sort = refList.sort();
        InternalDao dao = DaoCache.getInstance().get(elementClass);
        BaseQuery query = dao.query().in(Operator.ID, idList);
        List<BaseEntity> entityList;
        if(sort.equals(Default.SORT)){
            entityList = query.results();
        }else{
            entityList = query.sort(sort).results();
        }
        if(entityList.size() != len){
            len = entityList.size();
            arr = Array.newInstance(elementClass, len);
        }
        for(int i=0; i<len; i++){
            Array.set(arr, i, entityList.get(i));
        }
        return arr;
    }
    
    private static List fetchCollectionValue(Object val, Field field, Class elementClass){
        Collection<BaseEntity> collection = (Collection<BaseEntity>)val;
        if(collection.isEmpty()){
            return null;
        }
        List<String> idList = new ArrayList<>();
        for(BaseEntity ent : collection){
            if(ent != null){
                idList.add(ent.getId());
            }
        }
        RefList refList = field.getAnnotation(RefList.class);
        String sort = refList.sort();
        elementClass = FieldUtil.getRealType(elementClass, field);
        InternalDao dao = DaoCache.getInstance().get(elementClass);
        BaseQuery query = dao.query().in(Operator.ID, idList);
        List result;
        if(sort.equals(Default.SORT)){
            result = query.results();
        }else{
            result = query.sort(sort).results();
        }
        return result;
    }
    
    private static Map fetchMapValue(Object val, Field field) {
        Map map = (Map)val;
        if(map.isEmpty()){
            return null;
        }
        
        //for Map<K,V>, first to check the type of V
        ParameterizedType paramType = (ParameterizedType)field.getGenericType();
        Type[] types = paramType.getActualTypeArguments();
        boolean isArray = false;
        boolean isCollection = false;
        boolean isSingle = false;
        Class vType = null;
        Class elementType = null;
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
        
        //get value by different type of V
        Map result = new HashMap();
        Class<?> cls  = null;
        InternalDao dao = null;
        if(isSingle){
            cls = FieldUtil.getRealType((Class)types[1], field);
            dao = DaoCache.getInstance().get(cls);
        }
        for(Object key : map.keySet()){
            Object entryValue = map.get(key);
            if(entryValue == null){
                result.put(key, null);
                continue;
            }
            if(isSingle){
                BaseEntity refObj = (BaseEntity)entryValue;
                String id = refObj.getId();
                Object value = dao.findOne(id);
                result.put(key, value);
            }else if(isArray){
                Object arr = fetchArrayValue(entryValue, field, elementType);
                result.put(key, arr);
            }else if(isCollection){
                List list = fetchCollectionValue(entryValue, field, elementType);
                if(DataType.isListType(vType)){
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
