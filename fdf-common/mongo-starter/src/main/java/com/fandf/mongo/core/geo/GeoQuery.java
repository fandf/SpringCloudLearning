package com.fandf.mongo.core.geo;

import com.fandf.mongo.core.BaseDao;
import com.fandf.mongo.core.BaseQuery;
import com.fandf.mongo.core.utils.MapperUtil;
import com.fandf.mongo.core.utils.Operator;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class GeoQuery<T> extends BaseQuery<T> {
    
    public GeoQuery(BaseDao<T> dao){
        super(dao);
    }
    
    public GeoQuery<T> nearSphere(String key, Point point){
        DBObject geometry = new BasicDBObject();
        geometry.put(Operator.GEOMETRY, MapperUtil.toDBObject(point, true));
        append(key, Operator.NEAR_SPHERE, geometry);
        return this;
    }

    /**
     * 
     * @param key
     * @param point
     * @param maxDistance maximum meters
     * @return 
     */
    public GeoQuery<T> nearSphere(String key, Point point, double maxDistance){
        DBObject geometry = new BasicDBObject();
        geometry.put(Operator.GEOMETRY, MapperUtil.toDBObject(point, true));
        geometry.put(Operator.MAX_DISTANCE, maxDistance);
        append(key, Operator.NEAR_SPHERE, geometry);
        return this;
    }
    
    /**
     * 
     * @param key
     * @param point
     * @param maxDistance maximum meters
     * @param minDistance minimum meters
     * @return 
     */
    public GeoQuery<T> nearSphere(String key, Point point, double maxDistance, double minDistance){
        DBObject geometry = new BasicDBObject();
        geometry.put(Operator.GEOMETRY, MapperUtil.toDBObject(point, true));
        geometry.put(Operator.MAX_DISTANCE, maxDistance);
        geometry.put(Operator.MIN_DISTANCE, minDistance);
        append(key, Operator.NEAR_SPHERE, geometry);
        return this;
    }
    
    public GeoQuery<T> geoWithin(String key, GeoJSON geoJson){
        DBObject geometry = new BasicDBObject();
        geometry.put(Operator.GEOMETRY, MapperUtil.toDBObject(geoJson, true));
        append(key, Operator.GEO_WITHIN, geometry);
        return this;
    }
    
    public GeoQuery<T> geoIntersects(String key, GeoJSON geoJson){
        DBObject geometry = new BasicDBObject();
        geometry.put(Operator.GEOMETRY, MapperUtil.toDBObject(geoJson, true));
        append(key, Operator.GEO_INTERSECTS, geometry);
        return this;
    }
    
}
