FROM frolvlad/alpine-oraclejre8:latest
ADD order-center.jar /order-center.jar
EXPOSE 9900
ENTRYPOINT ["java","-Xms512m","-Xmx512m","-jar","order-center.jar","--spring.profiles.active=test2"]
