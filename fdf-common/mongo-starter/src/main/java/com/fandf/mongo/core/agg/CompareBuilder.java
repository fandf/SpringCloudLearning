package com.fandf.mongo.core.agg;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class CompareBuilder implements Builder {

    private static final String EQ = "$eq";
    private static final String NE = "$ne";
    private static final String GT = "$gt";
    private static final String GTE = "$gte";
    private static final String LT = "$lt";
    private static final String LTE = "$lte";

    private final DBObject dbo = new BasicDBObject();

    public CompareBuilder eq(String attr1, String attr2) {
        dbo.put(EQ, new String[]{attr1, attr2});
        return this;
    }

    public CompareBuilder notEquals(String attr1, String attr2) {
        dbo.put(NE, new String[]{attr1, attr2});
        return this;
    }

    public CompareBuilder greaterThan(String attr1, String attr2) {
        dbo.put(GT, new String[]{attr1, attr2});
        return this;
    }

    public CompareBuilder greaterThanEquals(String attr1, String attr2) {
        dbo.put(GTE, new String[]{attr1, attr2});
        return this;
    }

    public CompareBuilder lessThan(String attr1, String attr2) {
        dbo.put(LT, new String[]{attr1, attr2});
        return this;
    }

    public CompareBuilder lessThanEquals(String attr1, String attr2) {
        dbo.put(LTE, new String[]{attr1, attr2});
        return this;
    }

    @Override
    public DBObject build() {
        return dbo;
    }

}
