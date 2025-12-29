# Step 1: Use a lightweight Java 21 runtime as the base
FROM eclipse-temurin:21-jre-alpine

# Step 2: Set the working directory inside the container
WORKDIR /app

# Step 3: Copy the JAR file from your target folder to the container
# Replace 'JWTBearerToken-0.0.1-SNAPSHOT.jar' with your actual filename
COPY target/*.jar app.jar

# Step 4: Tell Docker which port your app runs on
EXPOSE 8083

# Step 5: Define the command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]