docker pull bladex/sentinel-dashboard:1.8.0

docker run -dit \
--name sentinel-nacos \
-p 8858:8858 \
--restart=always \
-e NACOS_SERVER_ADDR=host.docker.internal:8848 \
-e NACOS_USERNAME=nacos \
-e NACOS_PASSWORD=nacos \
-e NACOS_NAMESPACE=public \
-e NACOS_GROUP_ID=SENTINEL_GROUP \
bladex/sentinel-dashboard:1.8.0



1. 流量控制规则 (FlowRule)

|      Field       | 	说明                                           | 	默认值              |  
|:----------------:|:----------------------------------------------|:------------------|
|     resource     | 	资源名，资源名是限流规则的作用对象                            ||	 
|      count	      | 限流阈值	                                         ||
|      grade	      | 限流阈值类型，QPS 或线程数模式	                            | QPS 模式            |
|     limitApp     | 	流控针对的调用来源	                                   | default，代表不区分调用来源 |
|     strategy     | 	判断的根据是资源自身，还是根据其它关联资源 (refResource)，还是根据链路入口 | 	根据资源本身           |
| controlBehavior	 | 流控效果（直接拒绝 / 排队等待 / 慢启动模式）                     | 	直接拒绝             |

demo
````json
[
       {
        "resource": "/todolist/getTestList",
        "controlBehavior":0,
        "count":2,
        "grade":1,
        "limitApp": "default",
        "strategy":0
    },
     {
        "resource": "todolist/getTestListSentinelFlow",
        "controlBehavior":0,
        "count":2,
        "grade":1,
        "limitApp": "default",
        "strategy":0
    }
]
````


2. 熔断降级规则 (DegradeRule)

|    Field    | 	说明                     | 	默认值 |  
|:-----------:|:------------------------|:-----|
|  resource	  | 资源名，即限流规则的作用对象          ||	 
|   count	    | 阈值                      ||	 
|   grade	    | 降级模式，根据 RT 降级还是根据异常比例降级 | 	RT  |
| timeWindow	 | 降级的时间	                  ||

demo
````json
[
  {
  "resource": "todolist/getTestListSentinelDegrade",
  "grade":0,
  "count":1000,
  "timeWindow":3,
  "minRequestAmount": 2,
  "statIntervalMs":3000,
  "slowRatioThreshold":0.1
  }
]
````

3.系统自适应保护 (system)
````json
[
  {
    "resource": "todolist/system",
    "highestSystemLoad":3.0,
    "highestCpuUsage":0.05,
    "avgRt":10,
    "qps": 20,
    "maxThread":10
  }
]
````
4.热点规则配置(param-flow)
````json
[
  {
    "burstCount": 0,
    "clusterMode": false,
    "controlBehavior": 0,
    "count": 2,
    "durationInSec": 1,
    "grade": 1,
    "limitApp": "default",
    "maxQueueingTimeMs": 0,
    "paramFlowItemList": [
      {
      "classType": "int",
      "count": 5,
      "object": "1"
      }
    ],
    "paramIdx": 0,
    "resource": "todolist/getById"
  }
]
````
5.黑白名单配置(authority)  
例：给ip为10.18.xx.xx的请求来源添加对资源/todolist/getTestList1访问的黑名单。
````json
[
    {
        "resource": "/todolist/getTestList1",
        "limitApp": "10.18.xx.xx",
        "strategy": 1
    }
]
````
###### 参考文章 
>https://blog.csdn.net/ZHANGLIZENG/article/details/121855667