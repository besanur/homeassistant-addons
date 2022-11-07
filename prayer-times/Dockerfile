ARG BUILD_FROM

# Builder image
FROM eclipse-temurin:17-jdk as builder

# Create a custom Java runtime
RUN export JAVA_TOOL_OPTIONS="-Djdk.lang.Process.launchMechanism=vfork" && \
    $JAVA_HOME/bin/jlink \
    --add-modules java.base,java.logging,java.naming,java.desktop,java.management,java.security.jgss,java.instrument,java.sql \
    --strip-debug \
    --no-man-pages \
    --no-header-files \
    --compress=2 \
    --output /javaruntime

WORKDIR /workspace/app

COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:go-offline
COPY pom.xml .
COPY src src

RUN ./mvnw package -DskipTests

# Define base image
FROM $BUILD_FROM

ENV JAVA_HOME=/opt/java/openjdk
ENV PATH "${JAVA_HOME}/bin:${PATH}"
COPY --from=builder /javaruntime $JAVA_HOME
COPY --from=builder /workspace/app/target/*.jar /app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]