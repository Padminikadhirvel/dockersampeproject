# Use Maven image with JDK 17 for building and running the project
FROM maven:3.8.6-eclipse-temurin-17

# Set the working directory
WORKDIR /app

# Copy the project files to the container
COPY . .

# Install dependencies and compile the project
RUN mvn clean compile

# Command to execute tests directly
CMD ["mvn", "test"]












# Use an official Maven image to build the project
# FROM maven:3.8.5-openjdk-17 AS build
# FROM maven:3.8.1-jdk-11 AS builder

# Set the working directory
# WORKDIR /home/docker_seleniumgridFW_withdata

# copying src of framework
# COPY src /home/docker_seleniumgridFW_withdata/src

# copying data of framework
# COPY data /home/docker_seleniumgridFW_withdata/data

# copying pom.xml of framework
# COPY pom.xml /home/docker_seleniumgridFW_withdata

# copying testng.xml of framework
# COPY testng.xml /home/docker_seleniumgridFW_withdata

# Build the Maven project
# RUN mvn clean install

# Use a minimal Java runtime image for running the tests
# FROM openjdk:17-jdk-slim
# FROM openjdk:11-jre-slim

# Copy the built JAR files from the builder stage
# COPY --from=builder /home/docker_seleniumgridFW_withdata/target/docker_seleniumgrid_withdata-1.0.jar /home/docker_seleniumgridFW_withdata/docker_seleniumgrid_withdata-1.0.jar

# Set the entrypoint to run your test
# ENTRYPOINT ["java", "-jar", "/home/docker_seleniumgridFW_withdata/docker_seleniumgrid_withdata-1.0.jar"]


# Running the actual command
# RUN mvn -f /home/docker_seleniumgridFW_withdata/pom.xml clean test -DskipTests=true

# Copy the Excel file (if needed, but the path will be dynamic later)
# COPY src/test/resources/data.xlsx /app/data.xlsx

