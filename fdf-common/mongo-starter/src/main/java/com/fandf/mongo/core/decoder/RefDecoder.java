package com.fandf.mongo.core.decoder;

import com.fandf.mongo.core.BaseEntity;
import com.fandf.mongo.core.access.InternalDao;
import com.fandf.mongo.core.annotations.Default;
import com.fandf.mongo.core.annotations.Ref;
import com.fandf.mongo.core.cache.ConstructorCache;
import com.fandf.mongo.core.cache.DaoCache;
import com.fandf.mongo.core.utils.FieldUtil;
import com.fandf.mongo.core.utils.ReferenceUtil;
import com.mongodb.DBObject;

import java.lang.reflect.Field;

public class RefDecoder extends AbstractDecoder{
    
    private final Ref ref;
    
    public RefDecoder(Field field, DBObject dbo){
        super(field);
        ref = field.getAnnotation(Ref.class);
        String fieldName = field.getName();
        String name = ref.name();
        if(!name.equals(Default.NAME)){
            fieldName = name;
        }
        value = dbo.get(fieldName);
    }
    
    @Override
    public void decode(Object obj){
        String refId = ReferenceUtil.fromDbReference(ref, value);
        Class<?> clazz = FieldUtil.getRealType(field);
        BaseEntity refObj = null;
        //not cascade read
        if(ref.cascade().toUpperCase().indexOf(Default.CASCADE_READ)==-1 || withoutCascade){
            refObj = (BaseEntity) ConstructorCache.getInstance().create(clazz);
            refObj.setId(refId);
        }
        //cascade read
        else {
            InternalDao dao = DaoCache.getInstance().get(clazz);
            refObj = (BaseEntity)dao.findOneLazily(refId, true);
        }
        FieldUtil.set(obj, field, refObj);
    }
    
}
