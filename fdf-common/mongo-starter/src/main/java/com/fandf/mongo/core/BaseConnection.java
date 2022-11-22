package com.fandf.mongo.core;

import com.mongodb.*;

import java.util.List;

public interface BaseConnection {
    
    public void connect(String host, int port, String database);
    
    public void connect(String host, int port, String database, String username, String password);
    
    @Deprecated
    public void connect(List<ServerAddress> serverList, List<MongoCredential> credentialList, String database);
    
    public void connect();
    
    public void close();
    
    public BaseConnection setHost(String host);
    
    public BaseConnection setPort(int port);

    public BaseConnection setSource(String source);

    public BaseConnection setDatabase(String database);
    
    public BaseConnection setUsername(String username);
    
    public BaseConnection setPassword(String password);
    
    public BaseConnection setOptions(MongoClientOptions options);
    
    public BaseConnection setServerList(List<ServerAddress> serverList);
    
    @Deprecated
    public BaseConnection setCredentialList(List<MongoCredential> credentialList);
    
    public DB getDB();
    
    public MongoClient getMongoClient();
    
}
