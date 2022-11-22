package com.fandf.mongo.core.decoder;

import java.lang.reflect.Field;

public abstract class AbstractDecoder implements Decoder{
    
    protected Field field;
    protected Object value;
    
    protected boolean withoutCascade;
    
    protected AbstractDecoder(Field field){
        this.field = field;
    }
    
    @Override
    public boolean isNullField(){
        return value == null;
    }

    @Override
    public void setWithoutCascade(boolean withoutCascade) {
        this.withoutCascade = withoutCascade;
    }
    
}
