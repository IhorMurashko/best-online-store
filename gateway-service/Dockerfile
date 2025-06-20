FROM eclipse-temurin:21-jre

COPY target/gateway-service-*.jar /app.jar

EXPOSE 8083

ENTRYPOINT ["java", "-jar", "/app.jar"]