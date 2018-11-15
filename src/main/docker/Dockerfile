FROM openjdk:8-jdk-alpine
ADD lightmq-0.1.jar app.jar
# 注意时区问题
ENTRYPOINT ["java","-Duser.timezone=GMT+08","-jar","/app.jar"]
EXPOSE 8001