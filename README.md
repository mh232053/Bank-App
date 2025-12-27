# NovaBank - Production Banking Web App

A professional, Jenkins-ready Banking Web Application built with Java (Jakarta EE), JSP, and Maven.

## Features
- **Secure Authentication**: BCrypt password hashing and atomic session management.
- **Transactions**: Thread-safe money transfers between accounts.
- **Enterprise UI**: "App Shell" layout with Sidebar and Header (Jenkins-style).
- **Architecture**: Strict MVC (Model-View-Controller) with Service Layer.

## Tech Stack
- **Backend**: Java 17, Jakarta EE 10 (Servlets 6.0)
- **Frontend**: JSP, CSS3 (Enterprise Theme)
- **Build**: Maven 3.8
- **CI/CD**: Jenkins

## How to Run

### Local Development (NetBeans / IDE)
1. Open the project in NetBeans.
2. Ensure you have **Apache Tomcat 10.1+** installed.
3. Right-click project -> **Run** (or `Clean and Build`).
4. Access: `http://localhost:8080/bank-webapp`

### Credentials (Demo)
- **Alice**: `ali` / `1234`
- **Sara**: `sara` / `1234`

## Project Structure
- `src/main/java/com/bank/filter` - Security Filters
- `src/main/java/com/bank/service` - Business Logic (Synchronized)
- `src/main/java/com/bank/dao` - Data Access (ConcurrentHashMap)
- `src/main/webapp/WEB-INF/assets` - CSS & Static Assets

## Testing
Run unit tests via Maven:
```bash
mvn test
```
