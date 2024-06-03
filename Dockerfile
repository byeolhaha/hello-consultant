FROM openjdk:17-alpine

EXPOSE 8080

ARG JAR_FILE=build/libs/*.jar

COPY ${JAR_FILE} app.jar

ENTRYPOINT ["nohup", "java", "-jar", "/app.jar", ">", "nohup-prod.out", "2>&1", "&"]