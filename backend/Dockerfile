FROM openjdk:21-jdk-slim
ARG JAR_FILE=build/libs/manuel-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app_backend.jar
EXPOSE 8080
ENTRYPOINT [ "java", "-jar", "app_backend.jar" ]