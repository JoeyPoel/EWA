# Backend Setup
This document provides instructions on how to set up the backend for our project.

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

3. Build the Docker Image
   Create a Docker image for the project. Replace <tag> with an appropriate tag for your Docker image, which could be a version number or 'latest'.

```bash
docker build -t "solar3ewa/ewa-boolets-be:<tag>" -f Dockerfile .
```

4. Run the Docker Image
   Run the Docker image on your local machine. The application will be accessible on port 8080. Again, replace <tag> with the tag you used in the previous step.

```bash
docker run -p 8080:8080 "solar3ewa/ewa-boolets-be:<tag>"
```

## Testing
To execute the unit tests for the application, use the following command:

```bash
mvn test
```

## Deployment
The project is configured for continuous integration and deployment using GitLab CI/CD. Any changes pushed to the main branch will trigger an automated build and deployment process.

## Contributing
For information on contributing to this project, please read CONTRIBUTING.md. This document includes details on the code of conduct and the process for submitting pull requests.