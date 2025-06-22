FROM maven:3.8.6-amazoncorretto-17 as builder
WORKDIR /app
COPY . .
COPY target/eureka-service-registry-*.jar /app.jar

RUN mvn clean package -pl user-cart-service -am -Pprod -DskipTests -Dmaven.test.skip=true

FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY --from=builder /app/user-cart-service/target/*.jar app.jar
ENV SPRING_PROFILES_ACTIVE=prod
#EXPOSE 8080
EXPOSE 8091
ENTRYPOINT ["java", "-jar", "app.jar"]