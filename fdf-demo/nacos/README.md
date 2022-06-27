mysql创建nacos数据库执行以下脚本：
https://github.com/alibaba/nacos/blob/1.4.2/distribution/conf/nacos-mysql.sql

docker pull nacos/nacos-server:1.4.2


docker run -e JVM_XMS=256m -e JVM_XMX=256m -e MODE=standalone --name nacos -d -p 8848:8848 nacos/nacos-server:1.4.2

登录nacos 创建配置列表   
nacos-service.yaml 
> server:  
&nbsp;&nbsp;&nbsp;&nbsp;port: 8081

启动服务，出现
> Tomcat started on port(s): 8081 (http) with context path ''

则说明配置项启动成功。  
并且nacos管控台服务列表也有nacos-service服务。

## nacos 源码解析

待更新