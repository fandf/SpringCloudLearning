package com.fandf.mongo.core.agg;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

import java.util.ArrayList;
import java.util.List;

public final class BoolBuilder implements Builder {

    private final static String AND = "$and";
    private final static String OR = "$or";
    private final static String NOT = "$not";

    private String expression;

    private final List<DBObject> list = new ArrayList<>();

    public BoolBuilder and(String json1, String json2) {
        this.expression = AND;
        DBObject dbo1 = BasicDBObject.parse(json1);
        DBObject dbo2 = BasicDBObject.parse(json2);
        list.add(dbo1);
        list.add(dbo2);
        return this;
    }

    public BoolBuilder or(String json1, String json2) {
        this.expression = OR;
        DBObject dbo1 = BasicDBObject.parse(json1);
        DBObject dbo2 = BasicDBObject.parse(json2);
        list.add(dbo1);
        list.add(dbo2);
        return this;
    }

    public BoolBuilder not(String json) {
        this.expression = NOT;
        DBObject dbo = BasicDBObject.parse(json);
        list.add(dbo);
        return this;
    }

    @Override
    public DBObject build() {
        return new BasicDBObject(expression, list);
    }
}
