#FROM openjdk:21-jdk
#
#COPY target/eureka-service-registry-0.0.1-SNAPSHOT.jar /app.jar
#
#EXPOSE 8761
#
#CMD ["java", "-jar", "/app.jar"]

FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY target/*.jar app.jar

# EXPOSE необязателен для Railway, но полезен для локальной разработки
EXPOSE ${PORT:-8761}

# Важно: передаем порт через аргумент
ENTRYPOINT ["java", "-jar", "app.jar", "--server.port=${PORT}"]
