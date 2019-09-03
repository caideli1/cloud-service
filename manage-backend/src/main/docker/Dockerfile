FROM frolvlad/alpine-oraclejre8:latest
ADD manage-backend.jar /manage-backend.jar
EXPOSE 8700
ENTRYPOINT ["java","-Xms512m","-Xmx512m","-jar","manage-backend.jar","--spring.profiles.active=test2"]
