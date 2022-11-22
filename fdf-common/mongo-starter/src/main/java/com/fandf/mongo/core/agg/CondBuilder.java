package com.fandf.mongo.core.agg;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public final class CondBuilder implements Builder {

    private final static String COND = "$cond";
    private final static String IF = "if";
    private final static String THEN = "then";
    private final static String ELSE = "else";

    private final DBObject dbo = new BasicDBObject();

    public CondBuilder ifCondition(DBObject ifObj) {
        dbo.put(IF, ifObj);
        return this;
    }

    public CondBuilder ifCondition(String json) {
        DBObject ifObj = BasicDBObject.parse(json);
        dbo.put(IF, ifObj);
        return this;
    }

    public CondBuilder thenValue(Object value) {
        dbo.put(THEN, value);
        return this;
    }

    public CondBuilder elseValue(Object value) {
        dbo.put(ELSE, value);
        return this;
    }

    @Override
    public DBObject build() {
        return new BasicDBObject(COND, dbo);
    }

}
