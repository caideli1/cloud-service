FROM frolvlad/alpine-oraclejre8:latest
ADD oauth-center.jar /oauth-center.jar
EXPOSE 9999
ENTRYPOINT ["java","-Xms512m","-Xmx512m","-jar","oauth-center.jar","--spring.profiles.active=test2"]
