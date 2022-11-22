

package com.fandf.mongo.core.access;

import com.fandf.mongo.core.BaseDao;
import com.fandf.mongo.core.BaseQuery;
import com.fandf.mongo.core.utils.SortUtil;
import com.mongodb.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


@Deprecated
public class AdvancedDao<T> extends BaseDao<T> {
    
    public AdvancedDao(Class<T> clazz){
        super(clazz);
    }
    
    public Iterable<DBObject> mapReduce(MapReduceCommand cmd) {
        MapReduceOutput output = getCollection().mapReduce(cmd);
        return output.results();
    }
    
    public Iterable<DBObject> mapReduce(String map, String reduce) {
        MapReduceOutput output = getCollection().mapReduce(map, reduce, null, MapReduceCommand.OutputType.INLINE, null);
        return output.results();
    }
    
    public Iterable<DBObject> mapReduce(String map, String reduce, BaseQuery query) {
        return mapReduce(map, reduce, query.getCondition());
    }
    
    private Iterable<DBObject> mapReduce(String map, String reduce, DBObject query) {
        MapReduceOutput output = getCollection().mapReduce(map, reduce, null, MapReduceCommand.OutputType.INLINE, query);
        return output.results();
    }
    
    public Iterable<DBObject> mapReduce(String map, String reduce, String outputTarget, MapReduceCommand.OutputType outputType, String orderBy, BaseQuery query) {
        return mapReduce(map, reduce, outputTarget, outputType, orderBy, query.getCondition());
    }
    
    private synchronized Iterable<DBObject> mapReduce(String map, String reduce, String outputTarget, MapReduceCommand.OutputType outputType, String orderBy, DBObject query) {
        MapReduceOutput output = getCollection().mapReduce(map, reduce, outputTarget, outputType, query);
        DBCollection c = output.getOutputCollection();
        DBCursor cursor;
        if(orderBy != null){
            cursor = c.find().sort(SortUtil.getSort(orderBy));
        }else{
            cursor = c.find();
        }
        List<DBObject> list = new ArrayList<>();
        for(Iterator<DBObject> it = cursor.iterator(); it.hasNext(); ){
            list.add(it.next());
        }
        cursor.close();
        return list;
    }
    
    public Iterable<DBObject> mapReduce(String map, String reduce, String outputTarget, MapReduceCommand.OutputType outputType, String orderBy, int pageNum, int pageSize, BaseQuery query) {
        return mapReduce(map, reduce, outputTarget, outputType, orderBy, pageNum, pageSize, query.getCondition());
    }
    
    private synchronized Iterable<DBObject> mapReduce(String map, String reduce, String outputTarget, MapReduceCommand.OutputType outputType, String orderBy, int pageNum, int pageSize, DBObject query) {
        MapReduceOutput output = getCollection().mapReduce(map, reduce, outputTarget, outputType, query);
        DBCollection c = output.getOutputCollection();
        DBCursor cursor;
        if(orderBy != null){
            cursor = c.find().sort(SortUtil.getSort(orderBy)).skip((pageNum-1)*pageSize).limit(pageSize);
        }else{
            cursor = c.find().skip((pageNum-1)*pageSize).limit(pageSize);
        }
        List<DBObject> list = new ArrayList<>();
        for(Iterator<DBObject> it = cursor.iterator(); it.hasNext(); ){
            list.add(it.next());
        }
        cursor.close();
        return list;
    }
    
}
