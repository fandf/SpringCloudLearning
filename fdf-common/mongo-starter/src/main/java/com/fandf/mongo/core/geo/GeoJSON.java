package com.fandf.mongo.core.geo;


import com.fandf.mongo.core.utils.MapperUtil;

public abstract class GeoJSON {
    
    protected String type;

    public String getType() {
        return type;
    }
    
    @Override
    public String toString() {
        return MapperUtil.toDBObject(this, true).toString();
    }
    
}
