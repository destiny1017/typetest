FROM adoptopenjdk/openjdk11:latest
ARG JAR_FILE=./build/libs/*.jar
COPY ${JAR_FILE} ./typetest-app.jar
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=dev", "/typetest-app.jar"]