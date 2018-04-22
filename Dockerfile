FROM jrottenberg/ffmpeg:latest
MAINTAINER ceylog.wang

ADD docker/Jre8 /usr/local

ADD docker/xunfei_lib/libmsc32.so /lib/
ADD docker/xunfei_lib/libmsc64.so /lib64/

ENV JAVA_HOME /usr/local/Jre8
ENV CLASSPATH $JAVA_HOME/lib/dt.jar:$JAVA_HOME/lib/tools.jar
ENV PATH $PATH:$JAVA_HOME/bin

ADD ./target/tts-0.0.1-SNAPSHOT.jar /root/startup/app.jar
WORKDIR /root/startup/
EXPOSE 8080
ENTRYPOINT ["java","-Xms1024m","-Xmx1024m","-jar", "app.jar"]