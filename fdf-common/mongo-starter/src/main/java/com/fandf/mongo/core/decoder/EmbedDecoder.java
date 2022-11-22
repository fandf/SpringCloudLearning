package com.fandf.mongo.core.decoder;

import com.fandf.mongo.core.BaseEntity;
import com.fandf.mongo.core.annotations.Default;
import com.fandf.mongo.core.annotations.Embed;
import com.fandf.mongo.core.exception.AnnotationException;
import com.fandf.mongo.core.utils.FieldUtil;
import com.fandf.mongo.core.utils.MapperUtil;
import com.mongodb.DBObject;

import java.lang.reflect.Field;

public class EmbedDecoder extends AbstractDecoder{
        
    public EmbedDecoder(Field field, DBObject dbo){
        super(field);
        String fieldName = field.getName();
        Embed embed = field.getAnnotation(Embed.class);
        String name = embed.name();
        if(!name.equals(Default.NAME)){
            fieldName = name;
        }
        value = dbo.get(fieldName);
    }
    
    @Override
    public void decode(Object obj){
        Class<?> type = field.getType();
        if(type.isEnum()){
            FieldUtil.set(obj, field, Enum.valueOf((Class<Enum>)type, (String)value));
            return;
        }
        Object o = MapperUtil.fromDBObject(field.getType(), (DBObject)value);
        FieldUtil.set(obj, field, o);
        
        //tip for wrong use of @Embed
        if(o instanceof BaseEntity){
            throw new AnnotationException("The Embed object should not be BuguEntity!");
        }
    }
    
}
