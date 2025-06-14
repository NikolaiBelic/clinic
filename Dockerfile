FROM eclipse-temurin:11-jdk-focal
WORKDIR /app

# Copia el uberjar (asegúrate de que clinic.jar esté en la ruta correcta)
COPY build/distributions/uberJar/clinic.jar /app/app.jar

# Puerto expuesto (coincide con tu configuración Cuba)
EXPOSE 8080

# Ejecución con ajustes para producción
CMD ["java", "-server", "-XX:+UseG1GC", "-jar", "app.jar"]