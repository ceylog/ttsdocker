FROM jrottenberg/ffmpeg:latest
MAINTAINER ceylog.wang

ADD jdk-9.0.1 /usr/local/jdk9

ADD xunfei_lib/libmsc32.so /lib/
ADD xunfei_lib/libmsc64.so /lib64/

ENV JAVA_HOME /usr/local/jdk9
ENV CLASSPATH $JAVA_HOME/lib/dt.jar:$JAVA_HOME/lib/tools.jar
ENV PATH $PATH:$JAVA_HOME/bin

