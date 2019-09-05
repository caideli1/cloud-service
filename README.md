# cloud-service
1.bush和config的说明
#清除某一个服务的配置，从config服务调用
http://localhost:8000/actuator/bus-refresh?destination= collection-center:**

#清除所有的服务配置，从config服务调用
http://localhost:8000/actuator/bus-refresh

#未使用消息的时候，单独刷新某一个配置，从自身服务调用
http://localhost:9901/actuator/refresh
#这个要加上
management.endpoints.web.exposure.include: bus-refresh
# 加载所有的端点/默认只加载了 info / health
management.endpoints.web.exposure.include: "*"
