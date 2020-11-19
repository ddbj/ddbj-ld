FROM openjdk:11

RUN apt-get update
RUN apt-get -y install curl

VOLUME /tmp
RUN mkdir /batch
WORKDIR /batch
# jar target
ENV JAR_TARGET "ddbj-0.0.1-SNAPSHOT.jar"
# set entrypoint to execute spring boot application
ENTRYPOINT ["sh","-c","./entrypoint.sh"]
