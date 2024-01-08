# Frontend Setup
This document provides step-by-step instructions for setting up the frontend of our project.

## Prerequisites
Ensure you have the following installed:

- Node.js: Version 16.13.1
- npm: Version 8.1.2

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
The project is configured for continuous integration and deployment using GitLab CI/CD. Changes pushed to the main branch will automatically trigger a build and deployment process.

## Contributing
Contributions are welcome. Please refer to CONTRIBUTING.md for our code of conduct and guidelines on submitting pull requests.