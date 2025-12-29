# Step 1: Use a base image with Java 17
FROM openjdk:17-jdk-slim

# Step 2: Set the working directory inside the container
WORKDIR /app

# Step 3: Copy the JAR file from your target folder to the container
COPY target/*.jar app.jar

# Step 4: Tell the container which port to open
EXPOSE 8080

# Step 5: Define the command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]

# Use JDK 21 base image
FROM eclipse-temurin:21-jdk-jammy

# Create the working directory
WORKDIR /app

# Copy the JAR from your target folder
# Note: Ensure you ran 'mvn clean package' first!
COPY target/*.jar app.jar

# Expose the port
EXPOSE 8080

# Run the app
ENTRYPOINT ["java", "-jar", "app.jar"]