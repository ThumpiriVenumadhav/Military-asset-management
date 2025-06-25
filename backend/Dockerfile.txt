# Use Eclipse Temurin (OpenJDK 17) base image
FROM eclipse-temurin:17-jdk

# Create app directory
WORKDIR /app

# Copy everything into container
COPY . .

# Package the application
RUN ./mvnw clean install

# Expose the port your app runs on
EXPOSE 8080

# Run the Spring Boot app
CMD ["./mvnw", "spring-boot:run"]
