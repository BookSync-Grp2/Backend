FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/backend-0.0.1-SNAPSHOT.jar /app/backend.jar

ENV JWT_SECRET=${JWT_SECRET}

ENTRYPOINT ["java", "-jar", "backend.jar"]