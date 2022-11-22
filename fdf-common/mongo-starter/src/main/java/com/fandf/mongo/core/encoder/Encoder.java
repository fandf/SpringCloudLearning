package com.fandf.mongo.core.encoder;

public interface Encoder {
    
    public boolean isNullField();
    
    public String getFieldName();
    
    public Object encode();
    
    public void setWithoutCascade(boolean withoutCascade);
    
}
