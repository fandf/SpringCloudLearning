### 日志全链路追踪
配置文件demo:

```` yml
fdf:
#  是否开启审计日志
  audit-log:
    enabled: true
#    日志记录方式
    log-type: db
#    日志数据源
    datasource:
      driver-class-name: com.mysql.cj.jdbc.Driver
      jdbc-url: jdbc:mysql://${fdf.datasource.url}:3306/logger-center?useUnicode=true&characterEncoding=UTF-8&autoReconnect=true&useSSL=false&zeroDateTimeBehavior=convertToNull&serverTimezone=Asia/Shanghai
      username: ${fdf.datasource.username}
      password: ${fdf.datasource.password}
#  是否开启全链路日志追踪
  trace: true
````