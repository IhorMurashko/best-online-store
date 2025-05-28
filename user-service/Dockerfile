# Сборка слоя
FROM maven:3.8.6-amazoncorretto-17 AS builder

WORKDIR /app

# Копируем ВЕСЬ проект
COPY . .

# Сборка нужного модуля с зависимостями
RUN mvn clean package -pl user-service -am -Pprod -DskipTests -Dmaven.test.skip=true

# Финальный слой
FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

# Копируем собранный JAR из builder
COPY --from=builder /app/user-service/target/*.jar app.jar

ENV SPRING_PROFILES_ACTIVE=prod
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
