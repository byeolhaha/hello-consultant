FROM adoptopenjdk/openjdk17:latest

EXPOSE 8080

ARG JAR_FILE=build/libs/*.jar

COPY ${JAR_FILE} app.jar

ENTRYPOINT ["nohup", "java", "-jar", "/app.jar", ">", "nohup-prod.out", "2>&1", "&"]