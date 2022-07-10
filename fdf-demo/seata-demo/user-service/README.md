docker run -p 9005:9005 --name user-service \
--link logstash:logstash \
-v /etc/localtime:/etc/localtime \
-d fandf/user-service:1.0.0