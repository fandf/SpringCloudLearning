package com.fandf.mongo.core.agg;

import com.fandf.mongo.core.geo.Point;
import com.fandf.mongo.core.utils.MapperUtil;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public final class GeoNearOptions {

    private final static String SPHERICAL = "spherical";
    private final static String LIMIT = "limit";
    private final static String NUM = "num";
    private final static String MAX_DISTANCE = "maxDistance";
    private final static String MIN_DISTANCE = "minDistance";
    private final static String QUERY = "query";
    private final static String NEAR = "near";
    private final static String DISTANCE_FIELD = "distanceField";
    private final static String INCLUDE_LOCS = "includeLocs";

    public boolean spherical;
    public int limit;
    public int num;
    public double maxDistance;
    public double minDistance;
    public String query;  //json string
    public Point near;
    public String distanceField;
    public String includeLocs;

    public DBObject toDBObject() {
        DBObject dbo = new BasicDBObject();
        dbo.put(SPHERICAL, spherical);
        if (limit != 0) {
            dbo.put(LIMIT, limit);
        }
        if (num != 0) {
            dbo.put(NUM, num);
        }
        if (maxDistance != 0) {
            dbo.put(MAX_DISTANCE, maxDistance);
        }
        if (minDistance != 0) {
            dbo.put(MIN_DISTANCE, minDistance);
        }
        if (query != null) {
            dbo.put(QUERY, BasicDBObject.parse(query));
        }
        if (near != null) {
            dbo.put(NEAR, MapperUtil.toDBObject(near, true));
        }
        if (distanceField != null) {
            dbo.put(DISTANCE_FIELD, distanceField);
        }
        if (includeLocs != null) {
            dbo.put(INCLUDE_LOCS, includeLocs);
        }
        return dbo;
    }

}
