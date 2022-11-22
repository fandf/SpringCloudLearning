package com.fandf.mongo.core;

import com.fandf.mongo.core.agg.GeoNearOptions;
import com.fandf.mongo.core.agg.Lookup;
import com.fandf.mongo.core.parallel.Parallelable;
import com.fandf.mongo.core.utils.Operator;
import com.fandf.mongo.core.utils.SortUtil;
import com.fandf.mongo.core.utils.StringUtil;
import com.mongodb.AggregationOptions;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BaseAggregation<T> implements Parallelable {
    
    private final DBCollection coll;
    private final List<DBObject> pipeline = new ArrayList<>();
    
    private AggregationOptions options;
    
    public BaseAggregation(DBCollection coll){
        this.coll = coll;
    }
    
    public BaseAggregation setOptions(AggregationOptions options){
        this.options = options;
        return this;
    }
    
    /**
     * @since mongoDB 3.4
     * @param dbo
     * @return 
     */
    public BaseAggregation addFields(DBObject dbo){
        pipeline.add(new BasicDBObject(Operator.ADD_FIELDS, dbo));
        return this;
    }
    
    public BaseAggregation addFields(String jsonString){
        DBObject dbo = BasicDBObject.parse(jsonString);
        return addFields(dbo);
    }
    
    public BaseAggregation project(DBObject dbo){
        pipeline.add(new BasicDBObject(Operator.PROJECT, dbo));
        return this;
    }
    
    public BaseAggregation project(String jsonString){
        DBObject dbo = BasicDBObject.parse(jsonString);
        return project(dbo);
    }
    
    public BaseAggregation project(String key, Object val){
        return project(new BasicDBObject(key, val));
    }
    
    public BaseAggregation projectInclude(String... fields){
        DBObject dbo = new BasicDBObject();
        for(String field : fields){
            dbo.put(field, 1);
        }
        return project(dbo);
    }
    
    /**
     * @since mongoDB 3.4
     * @param fields
     * @return 
     */
    public BaseAggregation projectExclude(String... fields){
        DBObject dbo = new BasicDBObject();
        for(String field : fields){
            dbo.put(field, 0);
        }
        return project(dbo);
    }
    
    public BaseAggregation match(String key, Object value){
        DBObject dbo = new BasicDBObject(key, value);
        pipeline.add(new BasicDBObject(Operator.MATCH, dbo));
        return this;
    }
    
    public BaseAggregation match(DBObject dbo){
        pipeline.add(new BasicDBObject(Operator.MATCH, dbo));
        return this;
    }
    
    public BaseAggregation match(BaseQuery query){
        return match(query.getCondition());
    }
    
    public BaseAggregation match(String jsonString){
        DBObject dbo = BasicDBObject.parse(jsonString);
        return match(dbo);
    }
    
    public BaseAggregation lookup(DBObject dbo){
        pipeline.add(new BasicDBObject(Operator.LOOKUP, dbo));
        return this;
    }
    
    public BaseAggregation lookup(String jsonString){
        DBObject dbo = BasicDBObject.parse(jsonString);
        return lookup(dbo);
    }
    
    public BaseAggregation lookup(Lookup lookup){
        DBObject dbo = new BasicDBObject();
        dbo.put(Lookup.FROM, lookup.from);
        dbo.put(Lookup.LOCAL_FIELD, lookup.localField);
        dbo.put(Lookup.FOREIGN_FIELD, lookup.foreignField);
        dbo.put(Lookup.AS, lookup.as);
        return lookup(dbo);
    }
    
    public BaseAggregation limit(int n){
        pipeline.add(new BasicDBObject(Operator.LIMIT, n));
        return this;
    }
    
    public BaseAggregation skip(int n){
        pipeline.add(new BasicDBObject(Operator.SKIP, n));
        return this;
    }
    
    public BaseAggregation unwind(String field){
        if(! field.startsWith("$")){
            field = "$" + field;
        }
        pipeline.add(new BasicDBObject(Operator.UNWIND, field));
        return this;
    }
    
    public BaseAggregation unwindPreserveEmpty(String field){
        if(! field.startsWith("$")){
            field = "$" + field;
        }
        DBObject dbo = new BasicDBObject();
        dbo.put("path", field);
        dbo.put("preserveNullAndEmptyArrays", true);
        pipeline.add(new BasicDBObject(Operator.UNWIND, dbo));
        return this;
    }
    
    public BaseAggregation geoNear(DBObject dbo){
        pipeline.add(new BasicDBObject(Operator.GEO_NEAR, dbo));
        return this;
    }
    
    public BaseAggregation geoNear(String jsonString){
        DBObject dbo = BasicDBObject.parse(jsonString);
        return geoNear(dbo);
    }
    
    public BaseAggregation geoNear(GeoNearOptions options){
        DBObject dbo = options.toDBObject();
        return geoNear(dbo);
    }
    
    public BaseAggregation group(DBObject dbo){
        pipeline.add(new BasicDBObject(Operator.GROUP, dbo));
        return this;
    }
    
    public BaseAggregation group(String jsonString){
        DBObject dbo = BasicDBObject.parse(jsonString);
        return group(dbo);
    }
    
    public BaseAggregation replaceRoot(DBObject dbo){
        pipeline.add(new BasicDBObject(Operator.REPLACE_ROOT, dbo));
        return this;
    }
    
    public BaseAggregation replaceRoot(String jsonString){
        DBObject dbo = BasicDBObject.parse(jsonString);
        return replaceRoot(dbo);
    }
    
    public BaseAggregation replaceRoot(String key, Object value){
        if(StringUtil.isBlank(key) || !key.equals("newRoot")){
            throw new IllegalArgumentException("the key must be newRoot when use $replaceRoot!");
        }
        return replaceRoot(new BasicDBObject(key, value));
    }
    
    public BaseAggregation sort(DBObject dbo){
        pipeline.add(new BasicDBObject(Operator.SORT, dbo));
        return this;
    }
    
    public BaseAggregation sort(String jsonString){
        DBObject dbo = SortUtil.getSort(jsonString);
        pipeline.add(new BasicDBObject(Operator.SORT, dbo));
        return this;
    }
    
    public BaseAggregation sortAsc(String key){
        String ascString = SortUtil.asc(key);
        DBObject dbo = SortUtil.getSort(ascString);
        pipeline.add(new BasicDBObject(Operator.SORT, dbo));
        return this;
    }
    
    public BaseAggregation sortDesc(String key){
        String descString = SortUtil.desc(key);
        DBObject dbo = SortUtil.getSort(descString);
        pipeline.add(new BasicDBObject(Operator.SORT, dbo));
        return this;
    }
    
    public BaseAggregation out(String target){
        pipeline.add(new BasicDBObject(Operator.OUT, target));
        return this;
    }
    
    /**
     * @since mongoDB 3.4
     * @param resultName
     * @return 
     */
    public BaseAggregation count(String resultName){
        pipeline.add(new BasicDBObject(Operator.COUNT, resultName));
        return this;
    }
    
    /**
     * @since mongoDB 3.4
     * @param field
     * @return 
     */
    public BaseAggregation sortByCount(String field){
        if(! field.startsWith("$")){
            field = "$" + field;
        }
        pipeline.add(new BasicDBObject(Operator.SORT_BY_COUNT, field));
        return this;
    }
    
    @Override
    public Iterable<DBObject> results(){
        if(options == null){
            options = AggregationOptions.builder().build();
        }
        final Iterator<DBObject> it = coll.aggregate(pipeline, options);
        return new Iterable<DBObject>() {
            @Override
            public Iterator<DBObject> iterator() {
                return it;
            }
        };
    }
    
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("pipeline:");
        for(DBObject dbo : pipeline){
            sb.append(dbo.toString());
        }
        if(options != null){
            sb.append(" options:");
            sb.append(options.toString());
        }
        return sb.toString();
    }

}
