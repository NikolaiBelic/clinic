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

WORKDIR /app

# Configuración de zona horaria (AÑADIDO)
RUN apt-get update && \
    apt-get install -y tzdata && \
    ln -fs /usr/share/zoneinfo/Europe/Madrid /etc/localtime && \
    echo "Europe/Madrid" > /etc/timezone && \
    dpkg-reconfigure --frontend noninteractive tzdata

# Variables de entorno para la JVM (AÑADIDO)
ENV TZ=Europe/Madrid
ENV JAVA_OPTS="-Duser.timezone=Europe/Madrid"

# Copia solo el artefacto construido (ajusta la ruta según tu proyecto)
COPY --from=builder /app/build/distributions/uberJar/clinic.jar app.jar
COPY --from=builder /app/keystore.jks .
COPY --from=builder /app/jetty.xml .

EXPOSE 8080 8443

# Modificado el CMD para incluir JAVA_OPTS (AÑADIDO)
CMD ["sh", "-c", "java -server -XX:+UseG1GC $JAVA_OPTS -jar app.jar"]