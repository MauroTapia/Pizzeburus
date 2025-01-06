# Usamos Amazon Corretto para Java 11
FROM amazoncorretto:11

# Directorio de trabajo
WORKDIR /app

# Copiar el JAR de la aplicación al contenedor
COPY target/user-crud-1.0-SNAPSHOT.jar user-crud.jar

# Crear el directorio de logs
RUN mkdir -p /app/logs

# Exponer el puerto de la aplicación
EXPOSE 8083

# Ejecutar la aplicación Java
ENTRYPOINT ["java", "-Dlog4j.configurationFile=log4j2.xml", "-jar", "user-crud.jar"]