FROM openjdk:20-jdk-alpine
WORKDIR /app

COPY . .
RUN chmod +x ./mvnw
RUN ./mvnw clean package

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "target/LeatherBags-1.0.jar"]