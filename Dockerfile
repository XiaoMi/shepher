FROM maven:3.6-jdk-8-alpine AS builder
WORKDIR /usr/shepher

COPY . .
RUN mvn install

FROM java:8-jdk


MAINTAINER banchuanyu <banchuanyu@xiaomi.com>
MAINTAINER zhangpeng <zhangpeng@xiaomi.com>

ENV SHEPHER_HOME /usr/shepher
ENV BASE_DIR /usr/shepher

WORKDIR $SHEPHER_HOME

COPY --from=builder /usr/shepher/shepher-web/target/*.jar $SHEPHER_HOME/lib/
COPY --from=builder /usr/shepher/shepher-common/target/*.jar $SHEPHER_HOME/lib/
COPY --from=builder /usr/shepher/shepher-model/target/*.jar $SHEPHER_HOME/lib/
COPY --from=builder /usr/shepher/shepher-service/target/*.jar $SHEPHER_HOME/lib/

COPY --from=builder /usr/shepher/conf/* $SHEPHER_HOME/conf/
CMD java -jar lib/shepher-1.0.jar --spring.config.location=conf/application-base.properties,conf/application-docker.properties -Djava.ext.dirs=lib
