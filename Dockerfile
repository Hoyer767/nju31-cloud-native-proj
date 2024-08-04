FROM openjdk:17-jdk-slim

VOLUME /tmp

ADD target/demo-0.0.1-SNAPSHOT.jar cloud_native_proj.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","/cloud_native_proj.jar"]