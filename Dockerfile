FROM openjdk:21-jdk-slim

# Copy Files
WORKDIR /usr/src/app


COPY . .
# RUN ./mvnw dependency:go-offline
# Install

# RUN chmod +x mvnw

# RUN ./mvnw -Dmaven.test.skip=true package
# Download dependencies
# Download dependencies using Maven Wrapper
# RUN ./mvnw dependency:go-offline

# Build the application without running tests
RUN ./mvnw -Dmaven.test.skip=true package

# Docker Run Command
EXPOSE 8080
CMD ["java","-jar","/usr/src/app/target/playground-0.0.1-SNAPSHOT.jar"]