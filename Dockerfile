FROM java:8-jdk


MAINTAINER banchuanyu <banchuanyu@xiaomi.com>
MAINTAINER zhangpeng <zhangpeng@xiaomi.com>

ENV SHEPHER_HOME /usr/shepher
ENV BASE_DIR /usr/shepher

WORKDIR $SHEPHER_HOME

COPY ./lib/*.jar $SHEPHER_HOME/lib/
COPY ./bin/wait-for-it.sh $SHEPHER_HOME
COPY ./conf/* $SHEPHER_HOME/conf/
CMD ./wait-for-it.sh -t 300 db:3306 && java -jar lib/shepher-web-1.0.jar --spring.config.location=conf/application-base.properties,conf/application-docker.properties -Djava.ext.dirs=lib
