package com.fandf.mongo.core.encoder;

import com.fandf.mongo.core.access.InternalDao;
import com.fandf.mongo.core.annotations.Id;
import com.fandf.mongo.core.cache.DaoCache;
import com.fandf.mongo.core.exception.IdException;
import com.fandf.mongo.core.utils.Operator;
import org.bson.types.ObjectId;

import java.lang.reflect.Field;

public class IdEncoder extends AbstractEncoder {
    
    private final Id id;
    
    public IdEncoder(Object obj, Field field){
        super(obj, field);
        id = field.getAnnotation(Id.class);
    }
    
    @Override
    public boolean isNullField(){
        return false;
    }
    
    @Override
    public String getFieldName(){
        return Operator.ID;
    }
    
    @Override
    public Object encode() {
        Object result = null;
        switch(id.type()){
            case AUTO_GENERATE:
                if(value == null){
                    result = new ObjectId();
                }else{
                    result = new ObjectId(value.toString());
                }
                break;
            case AUTO_INCREASE:
                if(value == null){
                    InternalDao dao = DaoCache.getInstance().get(clazz);
                    long max = dao.getMaxId();
                    if(max == 0){
                        result = id.start();
                    }else{
                        result = max + 1L;
                    }
                }else{
                    result = Long.parseLong(value.toString());
                }
                break;
            case USER_DEFINE:
                if(value == null){
                    throw new IdException("user-defined id doesn't have value!");
                }else{
                    result = value.toString();
                }
                break;
        }
        return result;
    }
    
}
