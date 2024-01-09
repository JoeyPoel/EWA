# Backend Setup

This document provides instructions on how to set up the backend for our project, which is 
built using Java, Maven, and Docker.

- Link to go to the [Frontend Setup](/frontend/README.md)
- Link to go back to the [Main Page](../README.md)

## Prerequisites
- Java 17: Ensure Java 17 is installed and configured on your system.
- Maven 3.8.4: Maven is required for building and testing the project.
- Docker 19.03.12: Docker is used for building and running containerized versions of the application.

## Steps

1. Navigate to the Backend Directory
   Change into the backend directory of the project:

```bash
cd backend
```
2. Build the Project
   Build the project using Maven. This command compiles the application and packages it, skipping the tests:

```bash
mvn clean package -DskipTests
```

3. Run the Application for Development
   For development purposes, you can run the application directly using Maven:

```bash
mvn spring-boot:run
```
This will start the Spring Boot application using its embedded server. By default, it can be accessed at http://localhost:8080

## Testing
To execute the unit tests for the application, use the following command:

```bash
mvn test
```

## Deployment
The project is configured for continuous integration and deployment using GitLab CI/CD. Any changes 
pushed to the main branch will trigger an automated build and deployment process.

1. Docker Image Build
Create a Docker image for the project. Replace <tag> with an appropriate tag for your Docker image:

```bash
docker build -t "solar3ewa/ewa-boolets-be:<tag>" -f Dockerfile .
```
2. Docker Image Run
Run the Docker image on your local machine. The application will be accessible on port 8080:

```bash
docker run -p 8080:8080 "solar3ewa/ewa-boolets-be:<tag>"
```

3. Docker Image Push
Push the Docker image to a repository. Ensure you're logged into your Docker registry:

```bash
docker push "solar3ewa/ewa-boolets-be:<tag>"
```