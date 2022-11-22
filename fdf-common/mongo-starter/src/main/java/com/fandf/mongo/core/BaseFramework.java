package com.fandf.mongo.core;

import com.fandf.mongo.core.annotations.Default;
import com.fandf.mongo.core.utils.ThreadUtil;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BaseFramework {
    
    private final Map<String, BaseConnection> map = new ConcurrentHashMap<>();
    
    private ExecutorService executor;
    
    private int threadPoolSize;
    
    private BaseFramework(){
        if(threadPoolSize == 0){
            //default thread pool size: 2 * cpu + 1
            threadPoolSize = Runtime.getRuntime().availableProcessors() * 2 + 1;
        }
        executor = Executors.newFixedThreadPool(threadPoolSize);
    }
    
    private static class Holder {
        final static BaseFramework instance = new BaseFramework();
    } 
    
    public static BaseFramework getInstance(){
        return Holder.instance;
    }
    
    public BaseConnection createConnection(){
        return createConnection(Default.NAME);
    }
    
    public synchronized BaseConnection createConnection(String connectionName){
        BaseConnection connection = map.get(connectionName);
        if(connection == null){
            connection = new BasicConnection();
            map.put(connectionName, connection);
        }
        return connection;
    }

    public BaseConnection getConnection() {
        return map.get(Default.NAME);
    }
    
    public BaseConnection getConnection(String connectionName) {
        return map.get(connectionName);
    }

    public ExecutorService getExecutor() {
        return executor;
    }
    
    public void setThreadPoolSize(int threadPoolSize){
        this.threadPoolSize = threadPoolSize;
    }
    
    /**
     * destroy the framework, release all resource.
     */
    public void destroy(){
        //close the thread pool
        ThreadUtil.safeClose(executor);
        
        //close all the mongoDB connection
        Set<Entry<String, BaseConnection>> set = map.entrySet();
        for(Entry<String, BaseConnection> entry : set){
            BaseConnection conn = entry.getValue();
            conn.close();
        }
    }

}
