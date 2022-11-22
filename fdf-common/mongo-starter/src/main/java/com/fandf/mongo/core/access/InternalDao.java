package com.fandf.mongo.core.access;

import com.fandf.mongo.core.BaseDao;
import com.fandf.mongo.core.BaseEntity;
import com.fandf.mongo.core.annotations.Id;
import com.fandf.mongo.core.annotations.IdType;
import com.fandf.mongo.core.cache.FieldsCache;
import com.fandf.mongo.core.utils.IdUtil;
import com.fandf.mongo.core.utils.MapperUtil;
import com.fandf.mongo.core.utils.Operator;
import com.fandf.mongo.core.utils.StringUtil;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.WriteResult;

import java.lang.reflect.Field;


public final class InternalDao<T> extends BaseDao<T> {

    public InternalDao(Class<T> clazz) {
        super(clazz);
    }

    /**
     * Get the non-lazy fields.
     *
     * @return
     */
    public DBObject getKeys() {
        return keys;
    }

    /**
     * Get one entity without the lazy fields.
     *
     * @param id
     * @param withoutCascade
     * @return
     */
    public T findOneLazily(String id, boolean withoutCascade) {
        DBObject dbo = new BasicDBObject();
        dbo.put(Operator.ID, IdUtil.toDbId(clazz, id));
        DBObject result = getCollection().findOne(dbo, keys);
        return MapperUtil.fromDBObject(clazz, result, withoutCascade);
    }

    public WriteResult saveWithoutCascade(T t, boolean withoutCascade) {
        WriteResult wr;
        BaseEntity ent = (BaseEntity) t;
        if (StringUtil.isBlank(ent.getId())) {
            wr = doInsertWithoutCascade(t, withoutCascade);
        } else {
            Field idField = FieldsCache.getInstance().getIdField(clazz);
            Id idAnnotation = idField.getAnnotation(Id.class);
            if (idAnnotation.type() == IdType.USER_DEFINE) {
                if (this.exists(Operator.ID, ent.getId())) {
                    wr = doSaveWithoutCascade(ent, withoutCascade);
                } else {
                    wr = doInsertWithoutCascade(t, withoutCascade);
                }
            } else {
                wr = doSaveWithoutCascade(ent, withoutCascade);
            }
        }
        return wr;
    }

    private WriteResult doSaveWithoutCascade(BaseEntity ent, boolean withoutCascade) {
        if (hasCustomListener) {
            notifyUpdated(ent);
        }
        return getCollection().save(MapperUtil.toDBObject(ent, withoutCascade));
    }

    private WriteResult doInsertWithoutCascade(T t, boolean withoutCascade) {
        DBObject dbo = MapperUtil.toDBObject(t, withoutCascade);
        WriteResult wr = getCollection().insert(dbo);
        String id = dbo.get(Operator.ID).toString();
        BaseEntity ent = (BaseEntity) t;
        ent.setId(id);
        if (hasCustomListener) {
            notifyInserted(ent);
        }
        return wr;
    }

    /**
     * Get the max id value, for auto increased id type.
     *
     * @return
     */
    public synchronized long getMaxId() {
        double d = this.max(Operator.ID);
        return (long) d;
    }

}
