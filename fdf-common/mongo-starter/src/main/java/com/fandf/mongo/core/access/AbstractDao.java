package com.fandf.mongo.core.access;

import com.mongodb.DBCollection;


public abstract class AbstractDao {
    
    protected boolean split;
    
    private DBCollection coll;
    
    private static final ThreadLocal<DBCollection> local = new ThreadLocal<>();
    
    protected void setCollection(DBCollection coll) {
        if(split){
            local.set(coll);
        }else{
            this.coll = coll;
        }
    }

    public DBCollection getCollection() {
        if(split){
            return local.get();
        }else{
            return coll;
        }
    }
    
}
