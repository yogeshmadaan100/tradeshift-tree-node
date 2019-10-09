FROM maven:3.6.0-jdk-11-slim AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package

FROM openjdk:11-jre-slim
LABEL maintainer="Yogesh Madaan"
COPY --from=build /home/app/target/tree-structure.jar /usr/local/lib/tree-structure.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/usr/local/lib/tree-structure.jar"]