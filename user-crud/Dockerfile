FROM amazoncorretto:11

WORKDIR /app

COPY target/user-crud-1.0-SNAPSHOT.jar user-crud.jar

EXPOSE 8083

ENTRYPOINT ["java", "-jar", "user-crud.jar"]