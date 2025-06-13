FROM eclipse-temurin:11.0.26_4-jdk-focal

RUN apt-get update && apt-get install -y \
    fontconfig \
    libfreetype6 \
    && rm -rf /var/lib/apt/lists/*

WORKDIR /app

COPY build/distributions/uberJar/clinic.jar /app/
COPY modules/core/web/META-INF/jetty-env.xml /app/
COPY modules/core/src/com/company/clinic/app.properties /app/config/

RUN mkdir -p /app/{config,logs,temp,filestorage,storage} \
    && chown -R 1000:1000 /app

USER 1000

# Usa CMD en lugar de ENTRYPOINT para permitir sobrescritura
CMD ["java", "-jar", "clinic.jar", "--port=8080", "--contextName=app", "--jettyEnvPath=/app/jetty-env.xml"]