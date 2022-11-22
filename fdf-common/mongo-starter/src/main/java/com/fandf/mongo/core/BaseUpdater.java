package com.fandf.mongo.core;

import com.fandf.mongo.core.bitwise.Bitwise;
import com.fandf.mongo.core.cache.FieldsCache;
import com.fandf.mongo.core.utils.IdUtil;
import com.fandf.mongo.core.utils.MapperUtil;
import com.fandf.mongo.core.utils.Operator;
import com.fandf.mongo.core.utils.ReferenceUtil;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.WriteResult;

import java.util.*;
import java.util.Map.Entry;

public class BaseUpdater<T> {
    
    private final BaseDao<T> dao;
    
    private final DBObject modifier = new BasicDBObject();
    
    private boolean isolated = false;
    
    private boolean upsert = false;
    
    private boolean multi = true;
    
    public BaseUpdater(BaseDao<T> dao){
        this.dao = dao;
    }
    
    private Object checkSingleValue(String key, Object value){
        Class<T> clazz = dao.getEntityClass();
        Object result = value;
        if(value instanceof BaseEntity){
            BaseEntity be = (BaseEntity)value;
            result = ReferenceUtil.toDbReference(clazz, key, be.getClass(), be.getId());
        }else if(!(value instanceof DBObject) && 
                FieldsCache.getInstance().isEmbedField(clazz, key)){
            result = MapperUtil.toDBObject(value);
        }
        return result;
    }
    
    private Object checkArrayValue(String key, Object value){
        Class<T> clazz = dao.getEntityClass();
        Object result = value;
        if(value instanceof BaseEntity){
            BaseEntity be = (BaseEntity)value;
            result = ReferenceUtil.toDbReference(clazz, key, be.getClass(), be.getId());
        }else if(!(value instanceof DBObject) && 
                FieldsCache.getInstance().isEmbedListField(clazz, key)){
            result = MapperUtil.toDBObject(value);
        }
        return result;
    }
    
    private List checkRefListValue(String key, Collection collection){
        Class<T> clazz = dao.getEntityClass();
        List result = new ArrayList();
        for(Object o : collection){
            BaseEntity be = (BaseEntity) o;
            Object temp = ReferenceUtil.toDbReference(clazz, key, be.getClass(), be.getId());
            result.add(temp);
        }
        return result;
    }
    
    private List checkEmbedListValue(Collection collection){
        List result = new ArrayList();
        for(Object o : collection){
            result.add(MapperUtil.toDBObject(o));
        }
        return result;
    }
    
    private void append(String op, String key, Object value){
        Object obj = modifier.get(op);
        DBObject dbo;
        if(!(obj instanceof DBObject)) {
            dbo = new BasicDBObject(key, value);
            modifier.put(op, dbo);
        } else {
            dbo = (DBObject)modifier.get(op);
            dbo.put(key, value);
        }
    }

    public DBObject getModifier() {
        return modifier;
    }
    
    /**
     * execute the update operation on a single entity.
     * @param t
     * @return 
     */
    public WriteResult execute(T t){
        BaseEntity ent = (BaseEntity)t;
        return execute(ent.getId());
    }
    
    /**
     * execute the update operation on a single entity.
     * @param id
     * @return 
     */
    public WriteResult execute(String id){
        Class<T> clazz = dao.getEntityClass();
        DBObject condition = new BasicDBObject(Operator.ID, IdUtil.toDbId(clazz, id));
        WriteResult wr = dao.getCollection().update(condition, modifier, upsert, false); //update one
        if(dao.hasCustomListener){
            BaseEntity entity = (BaseEntity)dao.findOne(id);
            dao.notifyUpdated(entity);
        }
        return wr;
    }
    
    /**
     * execute the update operation on multi entity.
     * @param query
     * @return 
     */
    public WriteResult execute(BaseQuery query){
        return execute(query.getCondition());
    }
    
    /**
     * execute the update operation on all entity.
     * @return 
     */
    public WriteResult execute(){
        return execute(new BasicDBObject());
    }
    
    private WriteResult execute(DBObject condition){
        List ids = null;
        if(dao.hasCustomListener){
            ids = dao.getCollection().distinct(Operator.ID, condition);
        }
        if(isolated){
            condition.put(Operator.ISOLATED, 1);
        }
        WriteResult wr = dao.getCollection().update(condition, modifier, upsert, multi);
        if(dao.hasCustomListener && ids != null){
            DBObject in = new BasicDBObject(Operator.IN, ids);
            DBCursor cursor = dao.getCollection().find(new BasicDBObject(Operator.ID, in));
            List<T> list = MapperUtil.toList(dao.getEntityClass(), cursor);
            for(T t : list){
                dao.notifyUpdated((BaseEntity)t);
            }
        }
        return wr;
    }
    
    /**
     * Update entity's attribute.
     * @param key the field's name
     * @param value the field's new value
     * @return 
     */
    public BaseUpdater<T> set(String key, Object value){
        Class<T> clazz = dao.getEntityClass();
        FieldsCache cache = FieldsCache.getInstance();
        if(cache.isEmbedListField(clazz, key)) {
            Collection c = (Collection) value;
            value = checkEmbedListValue(c);
        }
        else if(cache.isRefListField(clazz, key)){
            Collection c = (Collection) value;
            value = checkRefListValue(key, c);
        }
        else{
            value = checkSingleValue(key, value);
        }
        append(Operator.SET, key, value);
        return this;
    }
    
    /**
     * Update entities with new key/value pairs.
     * @param map
     * @return 
     */
    public BaseUpdater<T> set(Map<String, Object> map){
        Set<Entry<String, Object>> set = map.entrySet();
        for(Entry<String, Object> entry : set){
            set(entry.getKey(), entry.getValue());
        }
        return this;
    }
    
    /**
     * Set one field(column) to current date.
     * @param key
     * @return 
     */
    public BaseUpdater<T> currentDate(String key){
        append(Operator.CURRENT_DATE, key, Boolean.TRUE);
        return this;
    }
    
    /**
     * Remove one filed(column) of an entity.
     * @param key the field name
     * @return 
     */
    public BaseUpdater<T> unset(String key){
        append(Operator.UNSET, key, 1);
        return this;
    }
    
     /**
     * Remove many fileds(column) of an entity.
     * @param keys the fields' name
     * @return 
     */
    public BaseUpdater<T> unset(String... keys){
        for(String key : keys){
            unset(key);
        }
        return this;
    }
    
    /**
     * Increase a numeric field.
     * @param key the field's name
     * @param value the numeric value to be added. It can be positive or negative integer, long, float, double.
     * @return 
     */
    public BaseUpdater<T> inc(String key, Number value){
        append(Operator.INC, key, value);
        return this;
    }
    
    /**
     * Decrease a numeric field.
     * @param key
     * @param value
     * @return 
     */
    public BaseUpdater<T> dec(String key, Number value){
        if(value instanceof Long || value instanceof Integer){
            return inc(key, value.longValue() * -1);
        }
        if(value instanceof Double || value instanceof Float){
            return inc(key, value.doubleValue() * -1);
        }
        throw new IllegalArgumentException("decrease value must be integer, long, double or float.");
    }
    
    /**
     * Multiply the value of a field by a number. 
     * @param key the field's name
     * @param value the numeric value to multiply
     * @return 
     */
    public BaseUpdater<T> mul(String key, Number value){
        append(Operator.MUL, key, value);
        return this;
    }
    
    /**
     * Adds a value to an array unless the value is already present, 
     * in which case does nothing to that array.
     * @param key
     * @param value
     * @return 
     */
    public BaseUpdater<T> addToSet(String key, Object value){
        value = checkArrayValue(key, value);
        append(Operator.ADD_TO_SET, key, value);
        return this;
    }
    
    /**
     * Add an element to entity's array/list/set field.
     * @param key the field's name
     * @param value the element to add
     * @return 
     */
    public BaseUpdater<T> push(String key, Object value){
        value = checkArrayValue(key, value);
        append(Operator.PUSH, key, value);
        return this;
    }
    
    /**
     * Add each element to the specified field.
     * @param key the field's name
     * @param valueList the list contains each element
     * @return 
     */
    public BaseUpdater<T> pushEach(String key, List valueList){
        int len = valueList.size();
        Object[] values = new Object[len];
        for(int i=0; i<len; i++){
            values[i] = checkArrayValue(key, valueList.get(i));
        }
        DBObject each = new BasicDBObject(Operator.EACH, values);
        append(Operator.PUSH, key, each);
        return this;
    }
    
    /**
     * Add each element to the specified field.
     * @param key the field's name
     * @param valueArray the array contains each element
     * @return 
     */
    public BaseUpdater<T> pushEach(String key, Object... valueArray){
        int len = valueArray.length;
        Object[] values = new Object[len];
        for(int i=0; i<len; i++){
            values[i] = checkArrayValue(key, valueArray[i]);
        }
        DBObject each = new BasicDBObject(Operator.EACH, values);
        append(Operator.PUSH, key, each);
        return this;
    }
    
    /**
     * Remove an element from entity's array/list/set field.
     * @param key the field's name
     * @param value the element to remove
     * @return 
     */
    public BaseUpdater<T> pull(String key, Object value){
        value = checkArrayValue(key, value);
        append(Operator.PULL, key, value);
        return this;
    }
    
    /**
     * Removes all instances of the specified values from an existing list.
     * @param key
     * @param valueList
     * @return 
     */
    public BaseUpdater<T> pullAll(String key, List valueList){
        int len = valueList.size();
        Object[] values = new Object[len];
        for(int i=0; i<len; i++){
            values[i] = checkArrayValue(key, valueList.get(i));
        }
        append(Operator.PULL_ALL, key, values);
        return this;
    }
    
    /**
     * Removes all instances of the specified values from an existing array.
     * @param key
     * @param valueArray
     * @return 
     */
    public BaseUpdater<T> pullAll(String key, Object... valueArray){
        int len = valueArray.length;
        Object[] values = new Object[len];
        for(int i=0; i<len; i++){
            values[i] = checkArrayValue(key, valueArray[i]);
        }
        append(Operator.PULL_ALL, key, values);
        return this;
    }
    
    /**
     * Remove the first element from the array/list/set field
     * @param key the field's name
     * @return 
     */
    public BaseUpdater<T> popFirst(String key){
        append(Operator.POP, key, -1);
        return this;
    }
    
    /**
     * Remove the last element from the array/list/set field
     * @param key the field's name
     * @return 
     */
    public BaseUpdater<T> popLast(String key){
        append(Operator.POP, key, 1);
        return this;
    } 
    
    /**
     * Update the value of the field to a specified value if the specified value is less than the current value of the field.
     * If the field does not exists, this operation sets the field to the specified value. 
     * @param key the field's name
     * @param value the specified value
     * @return 
     */
    public BaseUpdater<T> min(String key, Object value){
        append(Operator.MIN, key, value);
        return this;
    }
    
    /**
     * updates the value of the field to a specified value if the specified value is greater than the current value of the field.
     * If the field does not exists, this operation sets the field to the specified value. 
     * @param key the field's name
     * @param value the specified value
     * @return 
     */
    public BaseUpdater<T> max(String key, Object value){
        append(Operator.MAX, key, value);
        return this;
    }
    
    /**
     * Performs a bitwise update of a field
     * @param key the field's name
     * @param value the bitwise value
     * @param bitwise the enum type of bitwise operation: AND,OR,XOR
     * @return 
     */
    public BaseUpdater<T> bitwise(String key, int value, Bitwise bitwise){
        DBObject logic = new BasicDBObject(bitwise.toString().toLowerCase(), value);
        append(Operator.BIT, key, logic);
        return this;
    }
    
    /**
     * set the update operation is isolated or not.
     * If not set, default value is false.
     * @param isolated
     * @return 
     */
    public BaseUpdater<T> setIsolated(boolean isolated){
        this.isolated = isolated;
        return this;
    }

    /**
     * when true, inserts a document if no document matches the update query criteria.
     * If not set, default value is false.
     * @param upsert
     * @return 
     */
    public BaseUpdater<T> setUpsert(boolean upsert) {
        this.upsert = upsert;
        return this;
    }

    /**
     * when true, updates all documents in the collection that match the update query criteria, otherwise only updates one.
     * If not set, default value is true.
     * @param multi
     * @return 
     */
    public BaseUpdater<T> setMulti(boolean multi) {
        this.multi = multi;
        return this;
    }
    
    
}
