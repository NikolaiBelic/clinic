FROM eclipse-temurin:11.0.26_4-jdk-focal

RUN apt-get update && apt-get install -y \
    fontconfig \
    libfreetype6 \
    && rm -rf /var/lib/apt/lists/*

WORKDIR /app

# Copia el JAR y los archivos de configuración
COPY build/distributions/uberJar/clinic.jar /app/app.jar
COPY modules/core/web/META-INF/jetty-env.xml /app/jetty-env.xml
COPY modules/core/src/com/company/clinic/app.properties /app/config/

# Crea directorios necesarios
RUN mkdir -p /app/{config,logs,temp,filestorage,storage} \
    && chown -R 1000:1000 /app

USER 1000

# Configura el entrypoint para permitir comandos alternativos
ENTRYPOINT ["/bin/sh", "-c"]
CMD ["java -jar app.jar --port=8080 --contextName=app --jettyEnvPath=/app/jetty-env.xml"]