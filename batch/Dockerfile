FROM ubuntu
RUN apt-get update
RUN apt-get -y install openjdk-11-jdk
RUN apt-get -y install curl
ENV JAVA_HOME /usr/lib/jvm/java-11-openjdk-amd64
VOLUME /tmp
RUN mkdir /batch
WORKDIR /batch
# jar target
ENV JAR_TARGET "ddbj-0.0.1-SNAPSHOT.jar"
# set entrypoint to execute spring boot application
ENTRYPOINT ["sh","-c","./entrypoint.sh"]
