#FROM eclipse-temurin:21-jre
#
#COPY target/gateway-service-*.jar /app.jar
#
#EXPOSE 8083
#
#ENTRYPOINT ["java", "-jar", "/app.jar"]

FROM maven:3.8.6-amazoncorretto-17 AS builder
WORKDIR /app
COPY ../gateway-service .
RUN mvn clean package -pl gateway-service -am  -DskipTests -Dmaven.test.skip=true

FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY --from=builder /app/gateway-service/target/*.jar app.jar
ENV SPRING_PROFILES_ACTIVE=prod
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]