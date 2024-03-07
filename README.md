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

## Tech Stack

- [Java] - First class and official programming language for development.
- [Spring boot] - Used backend appliction 
- [Postgres SQL] - SQL database with ACID properties. 

## Video demonstrations

## RUN LOCALLY

```bash
git clone https://github.com/UtkarshTiwari99/LeanAssignment
```

```bash
./gradlew bootRun
```

 ## Project Structure

 com.utkarsh.leanassignment     # Root Package
    .
    ├──configurations         # Configuration for secuirty chain.
    ├── controller            # controllers to handel routing of requests.
    ├── dto                   # data tranfer object for both requests and response.
    ├── exception             # Custom Exceptions.
    ├── filters               # filter to intercept requests.
    ├── model                 # Model data classes. 
    ├── respository           # Layer to interact with data models using JPA.
    ├── service               # services for business logic. 
    ├── utils                 # utils for extra code. 
    ├─ SpringBootApplication  # Main class for Spring boot.
