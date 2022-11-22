package com.fandf.mongo.core.decoder;

public interface Decoder {
    
    public void decode(Object obj);
    
    public boolean isNullField();
    
    public void setWithoutCascade(boolean withoutCascade);
    
}
