package com.fandf.mongo.core;

import com.fandf.mongo.core.annotations.Id;
import com.fandf.mongo.core.cache.FieldsCache;
import com.fandf.mongo.core.exception.DBQueryException;
import com.fandf.mongo.core.parallel.Parallelable;
import com.fandf.mongo.core.utils.*;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.client.model.DBCollectionFindOptions;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

public class BaseQuery<T> implements Parallelable {

    protected final BaseDao<T> dao;

    protected DBObject slices;
    protected DBObject fields;
    protected boolean fieldsSpecified;  //default value is false

    protected DBObject condition = new BasicDBObject();

    protected String orderBy;
    protected int pageNumber;  //default value is zero
    protected int pageSize;  //default value is zero

    protected long maxTimeMS;

    protected boolean withoutCascade;

    public BaseQuery(BaseDao<T> dao) {
        this.dao = dao;
    }

    private void appendEquals(String key, String op, Object value) {
        Class<T> clazz = dao.getEntityClass();
        if (key.equals(Operator.ID)) {
            Object dbId = IdUtil.toDbId(clazz, (String) value);
            append(Operator.ID, op, dbId);
        } else if (key.indexOf(".") != -1) {
            append(key, op, value);
        } else {
            Field f = FieldsCache.getInstance().getField(clazz, key);
            if (f.getAnnotation(Id.class) != null) {
                Object dbId = IdUtil.toDbId(clazz, (String) value);
                append(Operator.ID, op, dbId);
            } else if (value instanceof BaseEntity) {
                BaseEntity ent = (BaseEntity) value;
                Object refObj = ReferenceUtil.toDbReference(clazz, key, ent.getClass(), ent.getId());
                append(key, op, refObj);
            } else if (f.getType().isEnum()) {
                if (value != null) {
                    append(key, op, value.toString());
                } else {
                    append(key, op, null);
                }
            } else {
                append(key, op, value);
            }
        }
    }

    private void appendThan(String key, String op, Object value) {
        Class<T> clazz = dao.getEntityClass();
        if (key.equals(Operator.ID)) {
            Object dbId = IdUtil.toDbId(clazz, (String) value);
            append(Operator.ID, op, dbId);
        } else if (key.indexOf(".") != -1) {
            append(key, op, value);
        } else {
            Field f = FieldsCache.getInstance().getField(clazz, key);
            if (f.getAnnotation(Id.class) != null) {
                Object dbId = IdUtil.toDbId(clazz, (String) value);
                append(Operator.ID, op, dbId);
            } else {
                append(key, op, value);
            }
        }
    }

    private void appendIn(String key, String op, Object... values) {
        if (key.equals(Operator.ID)) {
            append(Operator.ID, op, toIds(values));
        } else if (key.indexOf(".") != -1) {
            append(key, op, values);
        } else {
            Class<T> clazz = dao.getEntityClass();
            Field f = FieldsCache.getInstance().getField(clazz, key);
            if (f.getAnnotation(Id.class) != null) {
                append(Operator.ID, op, toIds(values));
            } else if (values.length != 0 && values[0] instanceof BaseEntity) {
                append(key, op, toReferenceList(key, values));
            } else if (f.getType().isEnum()) {
                List<String> list = new ArrayList<>();
                for (Object obj : values) {
                    list.add(obj.toString());
                }
                append(key, op, list);
            } else {
                append(key, op, values);
            }
        }
    }

    /**
     * Be careful! this method is hard to understand, but it is correct.
     *
     * @param key
     * @param op
     * @param value
     */
    protected void append(String key, String op, Object value) {
        if (op == null) {
            condition.put(key, value);
            return;
        }
        Object obj = condition.get(key);
        if (!(obj instanceof DBObject)) {
            DBObject dbo = new BasicDBObject(op, value);
            condition.put(key, dbo);
        } else {
            DBObject dbo = (DBObject) obj;
            dbo.put(op, value);
        }
    }

    private List<Object> toIds(Object... values) {
        List<Object> idList = new ArrayList<>();
        Class<T> clazz = dao.getEntityClass();
        int len = values.length;
        for (int i = 0; i < len; i++) {
            if (values[i] != null) {
                Object dbId = IdUtil.toDbId(clazz, (String) values[i]);
                idList.add(dbId);
            }
        }
        return idList;
    }

    private List<Object> toReferenceList(String key, Object... values) {
        List<Object> refList = new ArrayList<>();
        Class<T> clazz = dao.getEntityClass();
        int len = values.length;
        for (int i = 0; i < len; i++) {
            if (values[i] != null) {
                BaseEntity ent = (BaseEntity) values[i];
                Object refObj = ReferenceUtil.toDbReference(clazz, key, ent.getClass(), ent.getId());
                refList.add(refObj);
            }
        }
        return refList;
    }

    public BaseQuery<T> eq(String key, Object value) {
        appendEquals(key, null, value);
        return this;
    }

    public BaseQuery<T> notEquals(String key, Object value) {
        appendEquals(key, Operator.NE, value);
        return this;
    }

    public BaseQuery<T> text(String value) {
        return text(value, false);
    }

    public BaseQuery<T> text(String value, boolean caseSensitive) {
        DBObject dbo = new BasicDBObject();
        dbo.put(Operator.SEARCH, value);
        dbo.put(Operator.CASE_SENSITIVE, caseSensitive);
        condition.put(Operator.TEXT, dbo);
        return this;
    }

    public BaseQuery<T> or(BaseQuery... querys) {
        List list = (List) condition.get(Operator.OR);
        if (list == null) {
            list = new ArrayList();
            condition.put(Operator.OR, list);
        }
        for (BaseQuery q : querys) {
            list.add(q.getCondition());
        }
        return this;
    }

    public BaseQuery<T> and(BaseQuery... querys) {
        List list = (List) condition.get(Operator.AND);
        if (list == null) {
            list = new ArrayList();
            condition.put(Operator.AND, list);
        }
        for (BaseQuery q : querys) {
            list.add(q.getCondition());
        }
        return this;
    }

    public BaseQuery<T> nor(BaseQuery... querys) {
        List list = (List) condition.get(Operator.NOR);
        if (list == null) {
            list = new ArrayList();
            condition.put(Operator.NOR, list);
        }
        for (BaseQuery q : querys) {
            list.add(q.getCondition());
        }
        return this;
    }

    public BaseQuery<T> greaterThan(String key, Object value) {
        appendThan(key, Operator.GT, value);
        return this;
    }

    public BaseQuery<T> greaterThanEquals(String key, Object value) {
        appendThan(key, Operator.GTE, value);
        return this;
    }

    public BaseQuery<T> lessThan(String key, Object value) {
        appendThan(key, Operator.LT, value);
        return this;
    }

    public BaseQuery<T> lessThanEquals(String key, Object value) {
        appendThan(key, Operator.LTE, value);
        return this;
    }

    /**
     * Match: greaterThanEquals minValue and lessThanEquals maxValue.
     *
     * @param key
     * @param minValue
     * @param maxValue
     * @return
     */
    public BaseQuery<T> between(String key, Object minValue, Object maxValue) {
        greaterThanEquals(key, minValue);
        lessThanEquals(key, maxValue);
        return this;
    }

    /**
     * Match: lessThan minValue or greaterThan maxValue.
     *
     * @param key
     * @param minValue
     * @param maxValue
     * @return
     */
    public BaseQuery<T> notBetween(String key, Object minValue, Object maxValue) {
        DBObject dbo = new BasicDBObject();
        dbo.put(Operator.GTE, minValue);
        dbo.put(Operator.LTE, maxValue);
        append(key, Operator.NOT, dbo);
        return this;
    }

    public BaseQuery<T> in(String key, List list) {
        if (list == null || list.isEmpty()) {
            throw new IllegalArgumentException("$in query with empty value is not allowed.");
        }
        return in(key, list.toArray());
    }

    public BaseQuery<T> in(String key, Object... values) {
        if (values == null || values.length == 0) {
            throw new IllegalArgumentException("$in query with empty value is not allowed.");
        }
        appendIn(key, Operator.IN, values);
        return this;
    }

    public BaseQuery<T> notIn(String key, List list) {
        if (list == null || list.isEmpty()) {
            throw new IllegalArgumentException("$nin query with empty value is not allowed.");
        }
        return notIn(key, list.toArray());
    }

    public BaseQuery<T> notIn(String key, Object... values) {
        if (values == null || values.length == 0) {
            throw new IllegalArgumentException("$nin query with empty value is not allowed.");
        }
        appendIn(key, Operator.NIN, values);
        return this;
    }

    public BaseQuery<T> all(String key, List list) {
        if (list == null || list.isEmpty()) {
            throw new IllegalArgumentException("$all query with empty value is not allowed.");
        }
        return all(key, list.toArray());
    }

    public BaseQuery<T> all(String key, Object... values) {
        if (values == null || values.length == 0) {
            throw new IllegalArgumentException("$all query with empty value is not allowed.");
        }
        append(key, Operator.ALL, values);
        return this;
    }

    /**
     * Note: the regex string must in Java style, not JavaScript style.
     *
     * @param key
     * @param regexStr
     * @return
     */
    public BaseQuery<T> regex(String key, String regexStr) {
        append(key, null, Pattern.compile(regexStr));
        return this;
    }

    public BaseQuery<T> regexCaseInsensitive(String key, String regexStr) {
        append(key, null, Pattern.compile(regexStr, Pattern.CASE_INSENSITIVE));
        return this;
    }

    public BaseQuery<T> startsWith(String key, String prefixValue) {
        if (!prefixValue.startsWith("^")) {
            prefixValue = "^" + prefixValue;
        }
        return regex(key, prefixValue);
    }

    public BaseQuery<T> size(String key, int value) {
        append(key, Operator.SIZE, value);
        return this;
    }

    public BaseQuery<T> mod(String key, int divisor, int remainder) {
        append(key, Operator.MOD, new int[]{divisor, remainder});
        return this;
    }

    public BaseQuery<T> existsField(String key) {
        append(key, Operator.EXISTS, Boolean.TRUE);
        return this;
    }

    public BaseQuery<T> notExistsField(String key) {
        append(key, Operator.EXISTS, Boolean.FALSE);
        return this;
    }

    public BaseQuery<T> where(String whereStr) {
        append(Operator.WHERE, null, whereStr);
        return this;
    }

    /**
     * @param jsonStr
     * @return
     * @since mongoDB 3.6
     */
    public BaseQuery<T> expr(String jsonStr) {
        DBObject expression = BasicDBObject.parse(jsonStr);
        return expr(expression);
    }

    /**
     * @param expression
     * @return
     * @since mongoDB 3.6
     */
    public BaseQuery<T> expr(DBObject expression) {
        append(Operator.EXPR, null, expression);
        return this;
    }

    public BaseQuery<T> slice(String key, long num) {
        DBObject dbo = new BasicDBObject(Operator.SLICE, num);
        return addSlice(key, dbo);
    }

    public BaseQuery<T> slice(String key, long begin, long length) {
        DBObject dbo = new BasicDBObject(Operator.SLICE, new Long[]{begin, length});
        return addSlice(key, dbo);
    }

    private BaseQuery<T> addSlice(String key, DBObject dbo) {
        if (slices == null) {
            slices = new BasicDBObject();
        }
        slices.put(key, dbo);
        dao.getKeyFields().put(key, dbo);
        if (fields == null) {
            fields = new BasicDBObject();
        }
        fields.put(key, dbo);
        return this;
    }

    public BaseQuery<T> returnFields(String... fieldNames) {
        return specifyFields(1, fieldNames);
    }

    public BaseQuery<T> notReturnFields(String... fieldNames) {
        return specifyFields(0, fieldNames);
    }

    private BaseQuery<T> specifyFields(int value, String... fieldNames) {
        if (fields == null) {
            fields = new BasicDBObject();
        }
        for (String field : fieldNames) {
            //do not replace the $slice, if has been set
            if (fields.get(field) == null) {
                fields.put(field, value);
            }
        }
        fieldsSpecified = true;
        return this;
    }

    public BaseQuery<T> maxTimeMS(long maxTimeMS) {
        this.maxTimeMS = maxTimeMS;
        return this;
    }

    /**
     * @param orderBy JSON string to sort.
     * @return
     */
    public BaseQuery<T> sort(String orderBy) {
        this.orderBy = orderBy;
        return this;
    }

    /**
     * Order by a single key ascend
     *
     * @param key
     * @return
     */
    public BaseQuery<T> sortAsc(String key) {
        this.orderBy = SortUtil.asc(key);
        return this;
    }

    /**
     * Order by a single key descend
     *
     * @param key
     * @return
     */
    public BaseQuery<T> sortDesc(String key) {
        this.orderBy = SortUtil.desc(key);
        return this;
    }

    public BaseQuery<T> pageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
        return this;
    }

    public BaseQuery<T> pageSize(int pageSize) {
        this.pageSize = pageSize;
        return this;
    }

    public T result() {
        if (orderBy != null || pageNumber != 0 || pageSize != 0) {
            throw new DBQueryException("You should use results() to get a list, when you use sorting or pagination");
        }
        DBCollection coll = dao.getCollection();
        DBObject dbo;
        if (fieldsSpecified) {
            dbo = coll.findOne(condition, fields);
        } else if (slices != null) {
            dbo = coll.findOne(condition, slices);
        } else {
            dbo = coll.findOne(condition);
        }
        return MapperUtil.fromDBObject(dao.getEntityClass(), dbo, withoutCascade);
    }

    @Override
    public List<T> results() {
        DBObject projection;
        if (fieldsSpecified) {
            projection = fields;
        } else {
            projection = dao.getKeyFields();
        }

        DBCollectionFindOptions options = new DBCollectionFindOptions();
        options.projection(projection);
        if (maxTimeMS > 0) {
            options.maxTime(maxTimeMS, TimeUnit.MILLISECONDS);
        }
        if (orderBy != null) {
            options.sort(SortUtil.getSort(orderBy));
        }
        if (pageNumber > 0 && pageSize > 0) {
            options.skip((pageNumber - 1) * pageSize);
            options.limit(pageSize);
        }

        DBCollection coll = dao.getCollection();
        DBCursor cursor = coll.find(condition, options);
        return MapperUtil.toList(dao.getEntityClass(), cursor, withoutCascade);
    }

    /**
     * If collection is very large, count() will be slow, you should use countFast().
     *
     * @return
     */
    public long count() {
        return dao.getCollection().count(condition);
    }

    /**
     * If collection is very large, count() will be slow, you should use countFast().
     *
     * @return
     * @since mongoDB 3.4
     */
    public long countFast() {
        long counter = 0;
        Iterable<DBObject> results = dao.aggregate().match(condition).count("counter").results();
        Iterator<DBObject> it = results.iterator();
        if (it.hasNext()) {
            DBObject dbo = it.next();
            String s = dbo.get("counter").toString();
            counter = Long.parseLong(s);
        }
        return counter;
    }

    public boolean exists() {
        DBObject dbo = dao.getCollection().findOne(condition);
        return dbo != null;
    }

    /**
     * distinct() on large collection will fail. you should use distinctLarge().
     *
     * @param key
     * @return
     */
    public List distinct(String key) {
        return dao.getCollection().distinct(key, condition);
    }

    /**
     * distinct() on large collection will fail. you should use distinctLarge().
     *
     * @param key
     * @return
     */
    public List distinctLarge(String key) {
        List list = new ArrayList();
        Iterable<DBObject> results = dao.aggregate().match(condition).group("{_id:'$" + key + "'}").results();
        for (DBObject dbo : results) {
            list.add(dbo.get("_id"));
        }
        return list;
    }

    public DBObject getCondition() {
        return condition;
    }

    public void setCondition(DBObject condition) {
        this.condition = condition;
    }

    public DBObject getSort() {
        if (orderBy == null) {
            return null;
        }
        return SortUtil.getSort(orderBy);
    }

    public void setWithoutCascade(boolean withoutCascade) {
        this.withoutCascade = withoutCascade;
    }

    /**
     * Print query condition. Useful for log.
     *
     * @return
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("query condition:");
        sb.append(condition.toString());
        if (orderBy != null) {
            sb.append(" sort:");
            sb.append(SortUtil.getSort(orderBy).toString());
        }
        if (pageNumber > 0 && pageSize > 0) {
            sb.append(" skip:");
            sb.append((pageNumber - 1) * pageSize);
            sb.append(" limit:");
            sb.append(pageSize);
        }
        return sb.toString();
    }

}
