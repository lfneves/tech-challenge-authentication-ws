FROM  eclipse-temurin:17-jdk-focal

RUN mkdir /app

ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} /app/app.jar

WORKDIR /app

COPY docker-compose.yml /app/docker-compose.yml

EXPOSE 8099

ENTRYPOINT ["java","-jar","app.jar"]



