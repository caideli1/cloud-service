FROM frolvlad/alpine-oraclejre8:latest
ADD gateway-zuul.jar /gateway-zuul.jar
EXPOSE 8070
ENTRYPOINT ["java","-Xms512m","-Xmx512m","-jar","gateway-zuul.jar","--spring.profiles.active=test2"]
