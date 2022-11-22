package com.fandf.mongo.core.listener;

import com.fandf.mongo.core.BaseEntity;
import com.fandf.mongo.core.BaseQuery;
import com.fandf.mongo.core.access.InternalDao;
import com.fandf.mongo.core.annotations.Default;
import com.fandf.mongo.core.annotations.Ref;
import com.fandf.mongo.core.annotations.RefList;
import com.fandf.mongo.core.cache.DaoCache;
import com.fandf.mongo.core.cache.FieldsCache;
import com.fandf.mongo.core.utils.FieldUtil;
import com.fandf.mongo.core.utils.Operator;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unchecked")
public class CascadeDeleteListener implements EntityListener {
    
    private final List<Field> refFields = new ArrayList<>();
    private final List<Field> refListFields = new ArrayList<>();

    public CascadeDeleteListener(Class<?> clazz) {
        Field[] fields = FieldsCache.getInstance().get(clazz);
        for(Field f : fields){
            Ref ref = f.getAnnotation(Ref.class);
            if(ref!=null && ref.cascade().toUpperCase().indexOf(Default.CASCADE_DELETE)!=-1){
                refFields.add(f);
                continue;
            }
            RefList refList = f.getAnnotation(RefList.class);
            if(refList!=null && refList.cascade().toUpperCase().indexOf(Default.CASCADE_DELETE)!=-1){
                refListFields.add(f);
                continue;
            }
        }
    }

    @Override
    public void entityDeleted(BaseEntity entity) {
        for(Field f : refFields){
            processRef(entity, f);
        }
        for(Field f : refListFields){
            processRefList(entity, f);
        }
    }
    
    private void processRef(BaseEntity entity, Field f){
        Object value = FieldUtil.get(entity, f);
        if(value != null){
            Class<?> type = f.getType();
            InternalDao dao = DaoCache.getInstance().get(type);
            dao.remove(value);
        }
    }
    
    private void processRefList(BaseEntity entity, Field f){
        Object value = FieldUtil.get(entity, f);
        if(value == null){
            return;
        }
        List<String> idList = null;
        Class<?> clazz = null;
        Class<?> type = f.getType();
        if(type.isArray()){
            clazz = type.getComponentType();
            idList = getArrayIds(value);
        }else{
            ParameterizedType paramType = (ParameterizedType)f.getGenericType();
            Type[] types = paramType.getActualTypeArguments();
            int len = types.length;
            if(len == 1){
                clazz = (Class)types[0];
                idList = getCollectionIds(value);
            }else if(len == 2){
                clazz = (Class)types[1];
                idList = getMapIds(value);
            }
        }
        if(clazz == null){
            return;
        }
        if(idList != null && !idList.isEmpty()){
            InternalDao dao = DaoCache.getInstance().get(clazz);
            BaseQuery query = dao.query().in(Operator.ID, idList);
            dao.remove(query);
        }
    }
    
    private List<String> getArrayIds(Object value){
        int len = Array.getLength(value);
        List<String> idList = new ArrayList<>();
        for(int i=0; i<len; i++){
            Object item = Array.get(value, i);
            if(item != null){
                BaseEntity ent = (BaseEntity)item;
                idList.add(ent.getId());
            }
        }
        return idList;
    }
    
    private List<String> getCollectionIds(Object value){
        Collection<BaseEntity> collection = (Collection<BaseEntity>)value;
        List<String> idList = new ArrayList<>();
        for(BaseEntity ent : collection){
            if(ent != null){
                idList.add(ent.getId());
            }
        }
        return idList;
    }
    
    private List<String> getMapIds(Object value){
        Map<Object, BaseEntity> map = (Map<Object, BaseEntity>)value;
        Collection<BaseEntity> values = map.values();
        List<String> idList = new ArrayList<>();
        for(BaseEntity ent : values){
            if(ent != null){
                idList.add(ent.getId());
            }
        }
        return idList;
    }
    
    @Override
    public void entityInserted(BaseEntity entity) {
        //do nothing
    }

    @Override
    public void entityUpdated(BaseEntity entity) {
        //do nothing
    }

}
