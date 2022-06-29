创建本地Seata数据库  
https://github.com/seata/seata/blob/v1.5.1/script/server/db/mysql.sql  

docker run --name seata -p 8091:8091 -d  seataio/seata-server:1.5.1

docker cp seata:/seata-server /Users/dongfengfan/docker/seata


docker run -d  --name  seata -p 8091:8091 -p 7091:7091 -v /Users/dongfengfan/docker/seata/seata-server:/seata-server -e SEATA_PORT=8091 seataio/seata-server:1.5.1

修改本地 resources/application.yml

````yaml
server:
  port: 7091

spring:
  application:
    name: seata-server

logging:
  config: classpath:logback-spring.xml
  file:
    path: ${user.home}/logs/seata
  extend:
    logstash-appender:
      destination: 127.0.0.1:4560
    kafka-appender:
      bootstrap-servers: 127.0.0.1:9092
      topic: logback_to_logstash

console:
  user:
    username: seata
    password: seata

seata:
  config:
    # support: nacos, consul, apollo, zk, etcd3
    type: nacos
    nacos:
      server-addr: host.docker.internal:8848
      namespace:
      group: SEATA_GROUP
      username: nacos
      password: nacos
      ##if use MSE Nacos with auth, mutex with username/password attribute
      #access-key: ""
      #secret-key: ""
      data-id: seataServer.properties
  registry:
    # support: nacos, eureka, redis, zk, consul, etcd3, sofa
    type: nacos
    preferred-networks: 30.240.*
    nacos:
      application: seata-server
      server-addr: host.docker.internal:8848
      group: SEATA_GROUP
      namespace:
      cluster: default
      username: nacos
      password: nacos
  #  server:
  #    service-port: 8091 #If not configured, the default is '${server.port} + 1000'
  security:
    secretKey: SeataSecretKey0c382ef121d778043159209298fd40bf3850a017
    tokenValidityInMilliseconds: 1800000
    ignore:
      urls: /,/**/*.css,/**/*.js,/**/*.html,/**/*.map,/**/*.svg,/**/*.png,/**/*.ico,/console-fe/public/**,/api/v1/auth/login
````
docker restart seata

nacos 创建 seataServer.properties
````properties
 # 存储模式
store.mode=db
 
store.db.datasource=druid
store.db.dbType=mysql
# 需要根据mysql的版本调整driverClassName
# mysql8及以上版本对应的driver：com.mysql.cj.jdbc.Driver
# mysql8以下版本的driver：com.mysql.jdbc.Driver
store.db.driverClassName=com.mysql.jdbc.Driver
# 注意根据生产实际情况调整参数host和port
store.db.url=jdbc:mysql://host.docker.internal:3306/seata?rewriteBatchedStatements=true&useUnicode=true&characterEncoding=utf8&connectTimeout=1000&socketTimeout=3000&autoReconnect=true&useSSL=false
# 数据库用户名
store.db.user=root
# 用户名密码
store.db.password=123456
# 微服务里配置与这里一致
service.vgroupMapping.default_tx_group=default
````



````mysql
CREATE TABLE `account` (
  `id` bigint(20) NOT NULL,
  `user_id` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL,
  `money` int(8) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


INSERT INTO `account` (`id`, `user_id`, `money`) VALUES (1, '1', 100);
INSERT INTO `account` (`id`, `user_id`, `money`) VALUES (2, '2', 100);


CREATE TABLE `undo_log` (
                            `branch_id` bigint(20) NOT NULL COMMENT 'branch transaction id',
                            `xid` varchar(100) NOT NULL COMMENT 'global transaction id',
                            `context` varchar(128) NOT NULL COMMENT 'undo_log context,such as serialization',
                            `rollback_info` longblob NOT NULL COMMENT 'rollback info',
                            `log_status` int(11) NOT NULL COMMENT '0:normal status,1:defense status',
                            `log_created` datetime(6) NOT NULL COMMENT 'create datetime',
                            `log_modified` datetime(6) NOT NULL COMMENT 'modify datetime',
                            UNIQUE KEY `ux_undo_log` (`xid`,`branch_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='AT transaction mode undo table';

CREATE TABLE `storage` (
                           `id` int(11) NOT NULL,
                           `commodity_code` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
                           `count` int(8) DEFAULT NULL,
                           PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
INSERT INTO `storage` (`id`, `commodity_code`, `count`) VALUES (1, 'P1', 10);


CREATE TABLE `order` (
                         `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
                         `user_id` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
                         `commodity_code` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
                         `count` int(8) DEFAULT NULL,
                         `money` int(8) DEFAULT NULL,
                         PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
````


## 说明
**包括以下5个模块，分别是**

* `business-service`：业务服务
* `storage-service`：库存服务
* `order-service`：订单服务
* `user-service`：用户服务

**用户下单(`business-service`) -> 扣库存(`storage-service`) -> 创建订单(`order-service`) -> 减少账户余额(`user-service`)**

正常下单   localhost:9002/business/placeOrder    
扣除库存成功 > 创建订单成功 > 扣减账户余额成功


异常下单   localhost:9002/business/placeOrderFallBack  
扣除库存成功 > 创建订单成功 > 扣减账户余额失败，事务回滚