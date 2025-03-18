FROM eclipse-temurin:21-jre

WORKDIR /app

COPY target/teacher-case-2.0.jar teacher-case.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "teacher-case.jar"]