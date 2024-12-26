FROM amazoncorretto:11

WORKDIR /app

COPY target/gateway-server-1.0-SNAPSHOT.jar gateway-server.jar

EXPOSE 9000

ENTRYPOINT ["java", "-jar", "gateway-server.jar"]