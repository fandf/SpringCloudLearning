package com.fandf.mongo.core.base.connection;

import com.fandf.mongo.core.base.BasicTest;
import com.mongodb.MongoClientOptions;
import org.junit.Test;

/**
 * @author fandongfeng
 * @date 2022/11/19 17:38
 */
public class ConnectionTest extends BasicTest {

    @Test
    public void test(){
        connectDB();

        //do query here

        disconnectDB();
    }

    @Test
    public void testWithOptions(){
        //set connection pool size to 200. the default is 100.
        MongoClientOptions options = MongoClientOptions.builder().connectionsPerHost(200).build();

        connectDBWithOptions(options);

        //do query here

        disconnectDB();
    }

}
