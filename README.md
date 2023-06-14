# StudyMate

StudyMate is an innovative application designed to support the learning and education process. Our goal is to provide tools and resources that assist users in acquiring new skills and knowledge.

## Features

StudyMate offers a range of features that aid in the learning process:

- **User Module**: Manage your profile and track your progress.
- **Progress and Achievements Module**: Monitor your progress and the goals you've achieved.
- **Tests and Quizzes**: Test your knowledge with our tests and quizzes.
- **Educational Materials**: Access a wide range of educational materials to assist in your learning.
- **Learning Reminder**: Receive email notifications with reminders to study.

## Architecture

A key element of StudyMate's architecture is the hexagonal pattern combined with facades. This pattern allows for a clean separation of business logic from technical implementation, making the application easier to maintain and develop.
## Technologies

StudyMate was built with a variety of technologies to ensure a robust and scalable application:

- **Spring**: The application uses the Spring framework, including Spring Boot, for creating stand-alone, production-grade applications.

- **Maven**: Used for managing project's build.

- **Lombok**: A java library that automatically plugs into your editor and build tools, spicing up your java.

- **Mockito & MockMvc**: Used for unit tests and integration tests.

- **Docker & DockerCompose**: Used for creating, deploying, and running applications by using containers.

- **MongoDB**: The application's data is stored in a MongoDB database.

- **HTTP & RestTemplate**: Used for creating RESTful services.

- **Wiremock**: Used for mocking HTTP services for test purposes.

- **Scheduler**: Used for scheduling tasks.

- **TestContainers**: Used for providing throwaway instances of common databases, Selenium web browsers, or anything else that can run in a Docker container for use in JUnit tests.

- **Spring Data MongoDB**: Used for easier data access to MongoDB.

- **Repository**: Used for providing an abstraction of data access.

- **Clock**: Used for getting current time.

- **RestControllers & Rest API**: Used for creating RESTful controllers and APIs.

- **Validation**: Used for validating data.

- **Swagger**: Used for API documentation.

- **Redis Cache**: Used for caching data.

- **JWT**: Used for securing REST APIs.

- **Login & Registration**: The application provides user login and registration functionality.

- **Awaitility**: Used for testing asynchronous systems.

- **Bcrypt**: Used for password encoding.

- **Status Http**: Used for returning HTTP status codes.

## Usage

StudyMate provides a variety of endpoints for different functionalities. Here are some of the main ones:

### User Controller

- **Register**: `POST /api/users/register`
- **Login**: `POST /api/users/login`
- **Logout**: `POST /api/users/logout`
- **Get User Data**: `GET /api/users/{userId}`
- **Add Email to User**: `PUT /api/users/add-email`
- **Verify Email**: `POST /api/users/verify-email`

### Admin Controller

- **Get Users**: `GET /api/users`
- **Delete User**: `DELETE /api/users/{userId}`
- **Approve Educational Material**: `POST /api/educationalMaterials/{materialId}/approve`
- **Reject Educational Material**: `POST /api/educationalMaterials/{materialId}/reject`

### Reminder Controller

- **Get Reminders for Current User**: `GET /api/reminders`
- **Create Reminder**: `POST /api/reminders`
- **Delete Reminder**: `DELETE /api/reminders/{id}`

### Testing Module Controller

- **Create Test**: `POST /api/tests`
- **Get Test by ID**: `GET /api/tests/{id}`
- **Solve Test**: `POST /api/tests/{testId}/submit`
- **Get Test Results**: `GET /api/tests/{testId}/results`
- **Delete Test**: `DELETE /api/tests/{testId}`
- **Get Test Questions**: `GET /api/tests/{testId}/questions`
- **Get Question by ID**: `GET /api/tests/{testId}/questions/{questionId}`

### Educational Material Controller

- **Get Educational Materials**: `GET /api/educational-content`
- **Create Educational Material**: `POST /api/educational-content`
- **Update Educational Material**: `PUT /api/educational-content/{id}`
- **Get Material by ID**: `GET /api/educational-content/{id}`
- **Get Material Comments**: `GET /api/educational-content/{id}/comments`
- **Add Comment**: `POST /api/educational-content/{id}/comments`
- **Like Material**: `PUT /api/educational-content/{id}/likes`
- **Unlike Material**: `DELETE /api/educational-content/{id}/likes`
- **Delete Material**: `DELETE /api/educational-content/{id}`
- **Review Material**: `POST /api/educational-content/{id}/review`


You can interact with these endpoints using tools like curl or Postman. For more detailed information about the API, visit the Swagger UI at `http://localhost:8080/swagger-ui/index.html#/`.
