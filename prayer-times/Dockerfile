ARG BUILD_FROM
FROM maven:3.8.5-eclipse-temurin-17 as build
ARG JAR_FILE=target/*.jar

WORKDIR /workspace/app


COPY pom.xml .
COPY src src

RUN mvn -f pom.xml package -DskipTests

FROM $BUILD_FROM
COPY --from=build /workspace/app/target/*.jar /app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]