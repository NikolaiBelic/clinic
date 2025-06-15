# ---- Build Stage ----
FROM eclipse-temurin:11-jdk-focal as builder

WORKDIR /app

# Copia TODOS los archivos del proyecto (excepto lo excluido en .dockerignore)
COPY . .

# Ejecuta el build (con stacktrace para debug)
RUN chmod +x gradlew && \
    ./gradlew buildUberJar --no-daemon --build-cache --stacktrace

# ---- Runtime Stage ----
FROM eclipse-temurin:11-jdk-focal

# Configuración manual de timezone (AÑADIDO)
RUN ln -sf /usr/share/zoneinfo/Europe/Madrid /etc/localtime && \
    echo "Europe/Madrid" > /etc/timezone && \
    echo "export JAVA_TOOL_OPTIONS='-Duser.timezone=Europe/Madrid'" >> /etc/profile

WORKDIR /app

# Copia solo el artefacto construido (ajusta la ruta según tu proyecto)
COPY --from=builder /app/build/distributions/uberJar/clinic.jar app.jar
COPY --from=builder /app/keystore.jks .
COPY --from=builder /app/jetty.xml .

EXPOSE 8080 8443

# CMD modificado para incluir la zona horaria (AÑADIDO)
CMD ["sh", "-c", "java -server -XX:+UseG1GC -Duser.timezone=Europe/Madrid -jar app.jar"]