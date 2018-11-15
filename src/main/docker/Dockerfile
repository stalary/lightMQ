FROM openjdk:8-jdk-alpine
ENV TZ=Asia/Shanghai
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
ADD lightmq-0.1.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
EXPOSE 8001