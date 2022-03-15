#
# Oracle Java 8 Dockerfile
#
# https://github.com/dockerfile/java
# https://github.com/dockerfile/java/tree/master/oracle-java8
#

# Pull base image.
#FROM ascdc/jdk8


#FROM jdk8-alpine-dind-docker18
#WORKDIR /usr/local
#ARG JAR_FILE=eladmin-system/target/eladmin-system-2.6.jar
#COPY ${JAR_FILE} app.jar
#COPY eladmin-system/src/main/resources/config/ config/
#COPY eladmin-system/src/main/resources/config/application-prod.yml application-prod.yml
#EXPOSE 8000
#ENTRYPOINT["java","-jar","app.jar"]
#RUN java -jar app.jar

#CMD ["java","-jar","app.jar"]


# Docker image for springboot file run
# VERSION 0.0.1
# Author: eangulee
# 基础镜像使用java
FROM java:8
# 作者
MAINTAINER eangulee <eangulee@gmail.com>
# VOLUME 指定了临时文件目录为/tmp。
# 其效果是在主机 /var/lib/docker 目录下创建了一个临时文件，并链接到容器的/tmp
#VOLUME /tmp
# 将jar包添加到容器中并更名为app.jar
#ADD demo-0.0.1-SNAPSHOT.jar app.jar
WORKDIR /usr/local
ARG JAR_FILE=eladmin-system/target/eladmin-system-2.6.jar
COPY ${JAR_FILE} app.jar
COPY eladmin-system/src/main/resources/config/ config/
EXPOSE 8000
# 运行jar包
RUN bash -c 'touch app.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","app.jar"]