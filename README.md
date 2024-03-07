 ##   ABOUT THE PROJECT

This repository houses the codebase for the "Quiz Application" project, designed to facilitate user interaction with multiple-choice quizzes.The application boasts several essential features including uploading test data, commencing a test, retrieving specific questions, redirecting users based on their answers, storing user test results, and providing access to test results. 

## App Features

The application boasts various features, including:

   - Data Upload: 
   Users can upload test data from Excel sheets containing questions, options, and their corresponding relations.

   - Question Retrieval: 
   Users can retrieve specific questions based on unique identifiers, along with available options.

   - Adaptive Redirection: 
   Based on chosen answers, users are directed to the next question according to the test's structure.

   - Result Storage:
   User test actions are tracked, including attempted questions and the path of answered questions

   - Individual Results: 
   Users can access their test results by providing their ID, date, and time of the attempt.
   
   - Overall Results: 
   Access to all test results is provided in a paginated and chronologically sorted format.

## REST API ENDPOINTS

- Data Upload - POST "localhost:6795/upload"
- Question Retrieval - GET localhost:6795/tests/{testId}/questions/{questionNumber}
- Result Storage - PUT localhost:6795/testDataResult
- Individual Results - GET localhost:6795/testResult/{testId}
- Overall Results - GET localhost:6795/testResults?page={page}

## Tech Stack

- [Java] - First class and official programming language for development.
- [Spring boot] - Used backend appliction 
- [Postgres SQL] - SQL database with ACID properties. 

## RUN LOCALLY

```bash
git clone https://github.com/UtkarshTiwari99/LeanAssignment
```

```bash
./gradlew bootRun
```

 ## Project Structure
```bash
 com.utkarsh.leanassignment  # Root Package
├── configurations
│   ├── SecurityConfig.java     # Configuration for security chain (JWT)
├── controllers
│   ├── TestDataController.java     # Controller for handling user-related requests
├── dto
│   ├── QuestionDto.java           # Data Transfer Object for user data
├── exceptions
│   ├── CustomException.java     # Base class for custom exceptions
├── filters
│   ├── Filter.java      # Filter for logging requests/responses
├── model
│   ├── User.java              # Data model class representing a user
├── repositories
│   ├── UserRepository.java     # Interface for interacting with user data using JPA
├── services
│   ├── UserService.java        # Service class encapsulating user-related business logic
├── utils
│   ├── CommonUtils.java        # Utility class with common helper methods
└── SpringBootApplication.java  # Main class for starting the Spring Boot application
└── README.md                 # Project documentation and setup instructions
```
