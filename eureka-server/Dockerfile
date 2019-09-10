FROM java:8
LABEL maintainer="praveshgupta1993@gmail.com"
VOLUME /tmp
EXPOSE 8761
ADD /build/libs/eureka-server.jar eureka-server.jar
ENTRYPOINT ["java","-jar","eureka-server.jar"]