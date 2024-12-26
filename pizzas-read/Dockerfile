FROM amazoncorretto:11

WORKDIR /app

COPY target/pizzas-read-1.0-SNAPSHOT.jar pizzas-read.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "pizzas-read.jar"]