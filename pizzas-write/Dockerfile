FROM amazoncorretto:11

WORKDIR /app

COPY target/pizzas-write-1.0-SNAPSHOT.jar pizzas-write.jar

EXPOSE 8082

ENTRYPOINT ["java", "-jar", "pizzas-write.jar"]