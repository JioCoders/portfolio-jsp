#############################################################
# Stage 1: Build WAR file - Use Maven to build the WAR
FROM maven:3.8.5-openjdk-17 AS builder
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Stage 2: Deploy WAR to Tomcat - Use Tomcat to run the WAR
# FROM tomcat:9.0-jdk17
# WORKDIR /app
# COPY --from=builder /app/target/*.war /usr/local/tomcat/webapps/ROOT.war

# # Expose port 8080
# EXPOSE 8080
# CMD ["catalina.sh", "run"]

# Stage 2: Run the app with JDK - Run Spring Boot JAR
FROM eclipse-temurin:17-jdk
# FROM openjdk:17
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar

# Expose port 8080
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
#############################################################