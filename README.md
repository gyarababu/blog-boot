# Cipher-Blog
## _It is Java Spring Boot Application using Spring Security JWT Tokens, Maven Build Tool_

### Overview
This project is a Java Spring Boot application with Spring Security and JWT token authentication. It provides various features and enhancements, including:

- Role-based authentication and authorization.
- Versioning strategies for APIs.
- Swagger UI integration for API documentation.
- User registration and authentication.
- CRUD operations for posts and comments.
- DTO (Data Transfer Object) usage for improved API responses.
- Paging, sorting, and error handling.
- Bi-directional mappings between entities.

### Usage
Explain how to use your project once it's up and running. Include examples and screenshots if possible. You can describe how to:

- Register a user.
- Obtain JWT tokens.
- Make authenticated requests to your endpoints.

## Getting Started

Follow these steps to set up and run the project on your local machine:

### Prerequisites

Before you begin, ensure you have the following software installed on your system:

- Java Development Kit (JDK) 17 or higher
- Spring Boot 3.0.0 or higher
- Apache Maven (for building the project)
- MySQL

### Installation

1. Clone this repository to your local machine:

   ```bash
   git clone https://github.com/gyarababu/blog-boot.git
2. Navigate to the project directory: 

   ```bash
   cd your-project
3. Build the project:

   ```bash
   mvn clean install
4. Run the application:

   ```bash
   mvn spring-boot:run
## Usage
Explain how to use your project once it's up and running. Include examples and screenshots if possible. You can describe how to:

- Register a user.
- Obtain JWT tokens.
- Make authenticated requests to your endpoints.
## Configuration

- Database configuration.
- JWT token expiration time.
- Security roles and permissions.

## JWT Authentication

### Authentication
To access protected endpoints, you'll need to obtain a JWT token. You can do this by registering a user and then logging in.

### Example API Requests
Here are some example API requests you can try:

- Register a new user: POST http://localhost:8080/api/v1/auth/signup
- Obtain a JWT token: POST http://localhost:8080/api/v1/auth/signin
- Access a protected resource: GET http://localhost:8080/api/v1/posts

### Testing
You can use tools like Postman or Swagger UI to test the API endpoints. To access Swagger UI, visit:

http://localhost:8080/swagger-ui/index.html
