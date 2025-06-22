FROM eclipse-temurin:21-jre

COPY target/auth-service-*.jar /app.jar

EXPOSE 8082

ENTRYPOINT ["java", "-jar", "/app.jar", "--server.port=8082"]


