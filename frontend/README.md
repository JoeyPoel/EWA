# Frontend Setup

This document provides step-by-step instructions for setting up the frontend of our project, 
which utilizes Node.js and npm.

- Link to go to the [Backend Setup](/backend/README.md)
- Link to go back to the [Main Page](../README.md)

## Prerequisites
- Node.js: Version 16.13.1
- npm: Version 8.1.2
- Docker 19.03.12: Docker is used for building and running containerized versions of the application.


## Steps

1. Navigate to the Frontend Directory
   Change into the frontend directory of the project:

```bash
cd frontend
```
2. Install Dependencies
   Install the necessary dependencies using npm:

```bash
npm install
```
3. Start the Development Server
   Launch the development server. This will typically serve the app on http://localhost:8080/:

```bash
npm run serve
```
## Testing
Run the frontend unit tests with the following command:

```bash
npm run test
```
## Deployment
The project is configured for continuous integration and deployment using GitLab CI/CD. 
Changes pushed to the main branch will automatically trigger a build and deployment process. 
For if you want to do it manually follow the steps below.

1. Docker Image Build
   Create a Docker image for the project. Replace <tag> with an appropriate tag for your Docker image:

```bash
docker build -t "solar3ewa/ewa-boolets-fe:<tag>" -f Dockerfile .
```
2. Docker Image Run
   Run the Docker image locally. The app will be accessible on port 8080:

```bash
docker run -p 8080:8080 "solar3ewa/ewa-boolets-fe:<tag>"
```

3. Docker Image Push
   Push the Docker image to a repository. Make sure you are logged into your Docker registry:

```bash
docker push "solar3ewa/ewa-boolets-fe:<tag>"
```

## Additional npm Script Commands

Compiles and minifies the application.
```bash
npm run build
```

Compiles and minifies the application in production mode.

```bash
npm run buildprod
```

Compiles and minifies the application in development mode.

```bash
npm run builddev
```

Runs a local server to serve the built application, typically for testing the production build locally.

```bash
npm run start
```

Runs the linter for code quality checks and formatting.

```bash
npm run lint
```

Checks for outdated npm packages.

```bash
npm run check-versions
```

Updates npm packages to their latest versions.

```bash
npm run update
```

Runs an npm audit to identify and fix security vulnerabilities in the packages.

```bash
npm run audit
```