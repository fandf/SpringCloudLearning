package com.fandf.mongo.core.cache;

import com.fandf.mongo.core.access.InternalDao;

import java.lang.ref.SoftReference;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@SuppressWarnings("unchecked")
public class DaoCache {
    
    private final ConcurrentMap<String, SoftReference<InternalDao<?>>> cache = new ConcurrentHashMap<>();
    
    private DaoCache(){
        
    }
    
    private static class Holder {
        final static DaoCache instance = new DaoCache();
    } 
    
    public static DaoCache getInstance(){
        return Holder.instance;
    }
    
    public <T> InternalDao<T> get(Class<T> clazz){
        String name = clazz.getName();
        InternalDao<T> dao;
        boolean recycled = false;
        SoftReference<InternalDao<?>> sr = cache.get(name);
        if(sr != null){
            dao = (InternalDao<T>)sr.get();
            if(dao == null){
                recycled = true;
            }else{
                return dao;
            }
        }
        //if not exists
        dao = new InternalDao<>(clazz);
        sr = new SoftReference<>(dao);
        if(recycled){
            cache.put(name, sr);
            return dao;
        }else{
            SoftReference<InternalDao<?>> temp = cache.putIfAbsent(name, sr);
            if(temp != null){
                return (InternalDao<T>)temp.get();
            }else{
                return (InternalDao<T>)sr.get();
            }
        }
    }
    
}
