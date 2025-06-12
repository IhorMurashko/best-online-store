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
EXPOSE 8761
ENTRYPOINT ["java", "-jar", "app.jar"]
