package com.fandf.mongo.core;

import com.fandf.mongo.core.exception.BaseException;
import com.mongodb.*;

import java.util.List;

@SuppressWarnings("unchecked")
class BasicConnection implements BaseConnection {

    private String host;
    private int port = 27017;
    private String source;
    private List<ServerAddress> serverList;
    @Deprecated
    private List<MongoCredential> credentialList;
    private MongoCredential credential;
    private MongoClientOptions options;
    private String database;
    private String username;
    private String password;
    private MongoClient mongoClient;
    private DB db;

    /**
     * connect to a single mongodb server without auth.
     *
     * @param host
     * @param port
     * @param database
     */
    @Override
    public void connect(String host, int port, String database) {
        this.host = host;
        this.port = port;
        this.database = database;

        connect();
    }

    /**
     * connect to a single mongodb server.
     *
     * @param host
     * @param port
     * @param database
     * @param username
     * @param password
     */
    @Override
    public void connect(String host, int port, String database, String username, String password) {
        this.host = host;
        this.port = port;
        this.database = database;
        this.username = username;
        this.password = password;

        connect();
    }

    @Override
    @Deprecated
    public void connect(List<ServerAddress> serverList, List<MongoCredential> credentialList, String database) {
        this.serverList = serverList;
        this.credentialList = credentialList;
        this.database = database;

        connect();
    }

    /**
     * connect to mongodb with specified parameters.
     */
    @Override
    public void connect() {
        if (host != null && serverList != null) {
            throw new BaseException("Error when connect to database server! You should set database host or server list, but not both!");
        }
        if (username != null && password != null && database != null) {
            this.credential = MongoCredential.createScramSha1Credential(username, source, password.toCharArray());
        }
        if (options == null) {
            options = MongoClientOptions.builder().build();
        }
        if (host != null) {
            ServerAddress sa = new ServerAddress(host, port);
            if (credentialList != null) {
                mongoClient = new MongoClient(sa, credentialList, options);
            } else if (credential != null) {
                mongoClient = new MongoClient(sa, credential, options);
            } else {
                mongoClient = new MongoClient(sa, options);
            }
        } else if (serverList != null) {
            if (credentialList != null) {
                mongoClient = new MongoClient(serverList, credentialList, options);
            } else if (credential != null) {
                mongoClient = new MongoClient(serverList, credential, options);
            } else {
                mongoClient = new MongoClient(serverList, options);
            }
        } else {
            throw new BaseException("Error when connect to database server! You should set database host or server list, at least one!");
        }
        //get the database
        db = mongoClient.getDB(database);
    }

    @Override
    public void close() {
        if (mongoClient != null) {
            mongoClient.close();
            mongoClient = null;
        }
    }

    @Override
    public BaseConnection setHost(String host) {
        this.host = host;
        return this;
    }

    @Override
    public BaseConnection setPort(int port) {
        this.port = port;
        return this;
    }
    @Override
    public BaseConnection setSource(String source) {
        this.source = source;
        return this;
    }

    @Override
    public BaseConnection setDatabase(String database) {
        this.database = database;
        return this;
    }

    @Override
    public BaseConnection setUsername(String username) {
        this.username = username;
        return this;
    }

    @Override
    public BaseConnection setPassword(String password) {
        this.password = password;
        return this;
    }

    @Override
    public BaseConnection setOptions(MongoClientOptions options) {
        this.options = options;
        return this;
    }

    @Override
    public BaseConnection setServerList(List<ServerAddress> serverList) {
        this.serverList = serverList;
        return this;
    }

    @Override
    @Deprecated
    public BaseConnection setCredentialList(List<MongoCredential> credentialList) {
        this.credentialList = credentialList;
        return this;
    }

    @Override
    public DB getDB() {
        return db;
    }

    @Override
    public MongoClient getMongoClient() {
        return mongoClient;
    }

}
