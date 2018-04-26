FROM ceylog/ttsruntime:latest

MAINTAINER ceylog.wang
ARG JAR_FILE
ADD ./target/${JAR_FILE} /root/startup/app.jar
WORKDIR /root/startup/
EXPOSE 8080
ENTRYPOINT ["java","-Xms128m","-Xmx128m","-jar", "app.jar"]
