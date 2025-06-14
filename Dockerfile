FROM eclipse-temurin:11-jdk-focal
WORKDIR /app

RUN chmod +x ./gradlew
RUN ./gradlew buildUberJar clean --no-daemon --build-cache

# Copia el uberjar (asegúrate de que clinic.jar esté en la ruta correcta)
COPY build/distributions/uberJar/clinic.jar /app/app.jar
COPY keystore.jks /app/
COPY jetty.xml /app/

# Puerto expuesto (coincide con tu configuración Cuba)
EXPOSE 8080 8443

# Ejecución con ajustes para producción
CMD ["java", "-server", "-XX:+UseG1GC", "-jar", "app.jar"]