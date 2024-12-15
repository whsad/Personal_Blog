# Personal Blog Project Report

## Project Overview

**Personal Blog** is a web application for creating and managing articles. It consists of two sections:

- **Guest Section**: Accessible to all visitors, allowing them to view published articles.
- **Admin Section**: Restricted to the administrator, enabling article management (add, edit, delete).

The project demonstrates file-based storage for articles, basic authentication, and server-side rendering using Spring Boot, Spring Security, and Thymeleaf.

## Environment Requirements

- Java 1.8 or higher  
- Maven 3.6 or higher  

## Compilation and Running

1. Clone the project repository:

   ```bash
   git clone https://github.com/whsad/Personal_Blog.git
   cd Personal_Blog
   ```

2. Compile the project using Maven:

   ```bash
   mvn clean install
   ```

3. Run the application:

   ```bash
   java -jar target/Personal_Blog-0.0.1-SNAPSHOT.jar
   ```

4. Access the application in your browser:  

   - **Guest Section**: `http://localhost:8080/base/home`  
   - **Admin Section**: `http://localhost:8080/admin/home`  

## Specific Features

### Guest Section
- **Home Page**: Displays a list of all published articles with their titles and publication dates.
- **Article Page**: Shows the full content of an article.

### Admin Section
- **Dashboard**: Lists all articles with options to add, edit, or delete articles.
- **Add Article**: Provides a form to create a new article with title, content, and publication date fields.
- **Edit Article**: Allows modification of existing articles.
- **Delete Article**: Enables the removal of unwanted articles.

## Project Structure

```
src/main/
├── java/com/kirito/personal_blog
│   ├── aspect                 # Aspect logic for handling HTTP requests
│   ├── common                 # Data models (e.g., Article class)
│   ├── config                 # Spring Security configuration
│   ├── controller             # Controller layer to handle user requests
│   ├── handler                # Custom exception handling and response logic
│   ├── service                # Business logic layer for managing articles (CRUD operations)
│   └── PersonalBlogApplication.java # Main application entry point
├── resources
│   ├── templates              # Thymeleaf HTML templates
│   │   ├── guest              # Guest section pages
│   │   └── admin              # Admin section pages
│   ├── static                 # Static resources directory
│   │   ├── css                # CSS files for styling
│   │   ├── file               # Directory for storing article data
│   │   └── font               # Font files
│   └── application.yml        # Application configuration file

```

## Notes

- Article information is stored in the `/resources/static/file` directory, with each article saved individually in JSON format.
- The default admin username and password are specified in the `application.yml` configuration file and can be modified as needed.
- Future project enhancements may include features such as article categorization, a commenting system, and using a database to store articles.
