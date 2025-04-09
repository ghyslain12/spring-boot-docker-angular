FROM maven:3.9.6-eclipse-temurin-21
WORKDIR /app
COPY . /app/
EXPOSE 8080
CMD ["mvn", "spring-boot:run", "-Dspring-boot.run.jvmArguments=-Dspring.devtools.restart.enabled=true"]