FROM frolvlad/alpine-oraclejre8:latest
ADD register-center.jar /register-center.jar
EXPOSE 8761
ENTRYPOINT ["java","-Xms512m","-Xmx512m","-jar","register-center.jar","--spring.profiles.active=test2"]
