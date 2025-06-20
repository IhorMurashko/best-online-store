FROM maven:3.8.6-amazoncorretto-17 as builder
WORKDIR /app
COPY . .
RUN mvn clean package -pl test -am  -DskipTests -Dmaven.test.skip=true

FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY --from=builder /app/test/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]