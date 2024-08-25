FROM openjdk:17-alpine

WORKDIR /

ARG JAR_FILE_PATH=/build/libs/lg-0.0.1-SNAPSHOT.jar


ENV ACTIVE_PROFILE="local"

COPY /${JAR_FILE_PATH} ROOT.jar

EXPOSE 23001

ENTRYPOINT ["java", "-jar", "-Xmx256m", "-Dspring.profiles.active=${ACTIVE_PROFILE}", "-Duser.timezone=Asia/Seoul", "-Dcom.sun.management.jmxremote", "-Djava.rmi.server.hostname=lawgg-crawling", "-Dcom.sun.management.jmxremote.ssl=false", "-Dcom.sun.management.jmxremote.authenticate=false", "ROOT.jar"]

