FROM openjdk:11-jdk-slim
WORKDIR /app
COPY modules/core/build/libs/app-core-0.1-SNAPSHOT.jar app.jar
COPY modules/core/src/com/company/clinic/app-prod.properties /app/config/
ENV CUBA_CONF_DIR=/app/config
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]


