package com.fandf.mongo.core.base;

import com.fandf.mongo.core.BaseConnection;
import com.fandf.mongo.core.BaseFramework;
import com.mongodb.MongoClientOptions;
import com.mongodb.ServerAddress;

import java.util.ArrayList;
import java.util.List;

/**
 * @author fandongfeng
 * @date 2022/11/19 17:44
 */
public class ReplicaSetBaseTest {

    protected void connectDB(){
        List<ServerAddress> serverList = new ArrayList<ServerAddress>();
        serverList.add(new ServerAddress("192.168.1.248", 27017));
        serverList.add(new ServerAddress("192.168.1.248", 27018));
        serverList.add(new ServerAddress("192.168.1.248", 27019));


        BaseConnection conn = BaseFramework.getInstance().createConnection();
        conn.setServerList(serverList)
                .setUsername("root")
                .setPassword("123456")
                .setDatabase("test")
                .connect();
    }

    protected void connectDBWithOptions(MongoClientOptions options){
        List<ServerAddress> serverList = new ArrayList<ServerAddress>();
        serverList.add(new ServerAddress("127.0.0.1", 27017));
        serverList.add(new ServerAddress("127.0.0.1", 27018));
        serverList.add(new ServerAddress("127.0.0.1", 27019));

        BaseConnection conn = BaseFramework.getInstance().createConnection();
        conn.setOptions(options);
        conn.setServerList(serverList);
        conn.setUsername("root");
        conn.setPassword("123456");
        conn.setDatabase("test");
        conn.connect();
    }

    protected void disconnectDB(){
        BaseFramework.getInstance().destroy();
    }

}
