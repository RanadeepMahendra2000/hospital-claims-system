# 1) Pick a small Java 17 image
FROM openjdk:17-jdk-slim

# 2) Switch into /app
WORKDIR /app

# 3) Copy your Spring-Boot jar
COPY target/*.jar app.jar

# 4) Expose port (Spring Boot default is 8080)
EXPOSE 8080

# 5) Start the app
ENTRYPOINT ["java","-jar","app.jar"]
