# Use Maven to build the WAR
FROM maven:3.8.5-openjdk-17 AS builder
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Use Tomcat to run the WAR
FROM tomcat:9.0
COPY --from=builder /app/target/*.war /usr/local/tomcat/webapps/ROOT.war

# Expose port 8080
EXPOSE 8080

CMD ["catalina.sh", "run"]
