FROM frolvlad/alpine-oraclejre8:latest
ADD config-center.jar /config-center.jar
EXPOSE 8000
ENTRYPOINT ["java","-Xms512m","-Xmx512m","-jar","config-center.jar","--spring.profiles.active=test2"]
