# Student Management System v2.0 - Quick Start Guide

## 🚀 Getting Started with Enhanced Features

### Prerequisites
- Java 17+
- Maven 3.9+
- MySQL 8.0+ (or PostgreSQL 12+)
- Docker & Docker Compose (optional)

---

## ⚡ 5-Minute Quick Start

### Option 1: Run Locally with Maven

```bash
# 1. Clone and navigate
cd StudentManagementSystem

# 2. Create MySQL database
mysql -u root -p < database/schema.sql

# 3. Build and run
mvn spring-boot:run

# 4. Access application
# Web UI: http://localhost:8080/sms
# API Docs: http://localhost:8080/sms/swagger-ui.html
# Login: admin / admin123
```

### Option 2: Run with Docker Compose (Recommended)

```bash
# 1. Navigate to project
cd StudentManagementSystem

# 2. Start all services
docker-compose up -d

# 3. Wait for services to be healthy (30 seconds)

# 4. Access application
# Web UI: http://localhost:8080/sms
# API Docs: http://localhost:8080/sms/swagger-ui.html
# Database: http://localhost:8081 (PHPMyAdmin)
# Login: admin / admin123
```

---

## 📚 Key Features Overview

### 1. REST API Endpoints

#### Create Student
```bash
curl -X POST http://localhost:8080/api/v1/students \
  -H "Content-Type: application/json" \
  -H "Authorization: Basic YWRtaW46YWRtaW4xMjM=" \
  -d '{
    "name": "John Doe",
    "email": "john@example.com",
    "phone": "9876543210",
    "gpa": 3.8,
    "status": "ACTIVE"
  }'
```

#### Get All Students (Paginated)
```bash
curl -X GET "http://localhost:8080/api/v1/students?page=0&size=10" \
  -H "Authorization: Basic YWRtaW46YWRtaW4xMjM="
```

#### Search by Name
```bash
curl -X GET "http://localhost:8080/api/v1/students/search/name?name=John&page=0&size=10" \
  -H "Authorization: Basic YWRtaW46YWRtaW4xMjM="
```

#### Filter by GPA Range
```bash
curl -X GET "http://localhost:8080/api/v1/students/search/gpa?minGpa=3.0&maxGpa=4.0" \
  -H "Authorization: Basic YWRtaW46YWRtaW4xMjM="
```

#### Get Statistics
```bash
curl -X GET http://localhost:8080/api/v1/students/statistics \
  -H "Authorization: Basic YWRtaW46YWRtaW4xMjM="
```

### 2. Interactive API Documentation

1. Navigate to: `http://localhost:8080/sms/swagger-ui.html`
2. Click on any endpoint to expand
3. Click "Try it out" to test
4. Provide authentication (admin/admin123)
5. Execute and see responses

### 3. Web User Interface

#### Login
- URL: `http://localhost:8080/sms/login`
- Username: `admin`
- Password: `admin123`

#### Dashboard
- View total students
- Check statistics
- See recent students

#### Student Management
- Create new students
- Search by name
- Filter by status
- Edit student information
- Delete students
- Export data

### 4. Database Management (PHPMyAdmin)

- URL: `http://localhost:8081`
- Username: `root`
- Password: `root`
- Database: `student_management_system`

---

## 🔐 Authentication

All API endpoints require Basic Authentication.

### Using cURL with Authentication
```bash
# Method 1: Using Authorization header
curl -H "Authorization: Basic YWRtaW46YWRtaW4xMjM=" \
  http://localhost:8080/api/v1/students

# Method 2: Using -u flag
curl -u admin:admin123 http://localhost:8080/api/v1/students
```

### Default Credentials
- **Username:** admin
- **Password:** admin123

### Change Default Credentials
Edit `application.properties`:
```properties
spring.security.user.name=your-username
spring.security.user.password=your-password
```

---

## 🗂️ Project Structure Reference

```
src/main/java/com/sms/
├── SpringBootApp.java                 # Main entry point
├── controller/
│   ├── StudentApiController.java      # REST endpoints
│   ├── WebUiController.java           # Web UI pages
│   ├── ApiResponse.java               # Response wrapper
│   └── StudentStatistics.java         # Stats DTO
├── service/
│   └── StudentApiService.java         # Business logic
├── repository/
│   └── StudentRepository.java         # Data access
├── model/
│   ├── StudentEntity.java             # Database entity
│   └── StudentStatus.java             # Status enum
├── dto/
│   ├── StudentDTO.java                # API DTO
│   └── StudentSearchRequest.java      # Search DTO
├── exception/
│   ├── GlobalExceptionHandler.java    # Error handling
│   └── ResourceNotFoundException.java  # Custom exception
└── config/
    ├── SecurityConfig.java            # Security config
    └── OpenApiConfig.java             # API docs config
```

---

## 🔧 Configuration Reference

### application.properties Key Settings

```properties
# Server
server.port=8080
spring.application.name=Student Management System

# Database (MySQL)
spring.datasource.url=jdbc:mysql://localhost:3306/student_management_system
spring.datasource.username=root
spring.datasource.password=root

# JPA
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=false

# Logging
logging.level.com.sms=DEBUG
logging.file.name=logs/application.log

# Security
spring.security.user.name=admin
spring.security.user.password=admin123

# Swagger
springdoc.swagger-ui.enabled=true
springdoc.swagger-ui.path=/swagger-ui.html
```

### Database Configuration

#### For MySQL
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/student_management_system
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
```

#### For PostgreSQL
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/student_management_system
spring.datasource.driver-class-name=org.postgresql.Driver
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQL10Dialect
```

---

## 🐳 Docker Commands

### Build Docker Image
```bash
docker build -t sms-app:2.0.0 .
```

### Run Docker Container
```bash
docker run -d \
  --name sms-app \
  -p 8080:8080 \
  -e SPRING_DATASOURCE_URL=jdbc:mysql://host.docker.internal:3306/student_management_system \
  sms-app:2.0.0
```

### Docker Compose Commands
```bash
# Start services
docker-compose up -d

# View logs
docker-compose logs -f app

# Stop services
docker-compose down

# Stop and remove volumes
docker-compose down -v
```

---

## 🧪 Testing the Application

### Unit Tests
```bash
mvn test
```

### Integration Tests
```bash
mvn verify
```

### Build with Tests
```bash
mvn clean package
```

### Skip Tests
```bash
mvn clean package -DskipTests
```

---

## 🔍 Common Tasks

### Check if Application is Running
```bash
# Health check
curl http://localhost:8080/sms/swagger-ui.html

# Or check logs
docker-compose logs app
```

### View Application Logs
```bash
# Local
tail -f logs/application.log

# Docker
docker-compose logs -f app
```

### Access Database
```bash
# Using MySQL CLI
mysql -u root -p student_management_system

# Using PHPMyAdmin
http://localhost:8081
```

### Restart Application
```bash
# Docker Compose
docker-compose restart app

# Local (Stop with Ctrl+C, then restart)
mvn spring-boot:run
```

### Clear Database
```bash
# MySQL
mysql -u root -p student_management_system < database/schema.sql

# Or drop and recreate
DROP DATABASE student_management_system;
CREATE DATABASE student_management_system;
```

---

## 📊 Sample API Requests

### 1. Create Multiple Students
```bash
for i in {1..5}; do
  curl -X POST http://localhost:8080/api/v1/students \
    -H "Content-Type: application/json" \
    -u admin:admin123 \
    -d "{
      \"name\": \"Student $i\",
      \"email\": \"student$i@example.com\",
      \"phone\": \"989999999$i\",
      \"gpa\": $((3 + i / 10)).$(printf "%02d" $((i * 10))),
      \"status\": \"ACTIVE\"
    }"
done
```

### 2. Get Pagination
```bash
# Page 0, size 10
curl -u admin:admin123 \
  "http://localhost:8080/api/v1/students?page=0&size=10"

# Page 1, size 5
curl -u admin:admin123 \
  "http://localhost:8080/api/v1/students?page=1&size=5"
```

### 3. Combined Search
```bash
# Search by status and GPA
curl -u admin:admin123 \
  "http://localhost:8080/api/v1/students/search/status?status=ACTIVE" \
  && curl -u admin:admin123 \
  "http://localhost:8080/api/v1/students/search/gpa?minGpa=3.5&maxGpa=4.0"
```

---

## 🆘 Troubleshooting

### Port Already in Use
```bash
# Change port in application.properties
server.port=8081

# Or kill existing process
netstat -ano | findstr :8080
taskkill /PID <PID> /F
```

### Database Connection Failed
```bash
# Check MySQL is running
mysqld --version

# Test connection
mysql -u root -p -e "SELECT 1"

# Update credentials in application.properties
```

### Docker Container Won't Start
```bash
# Check logs
docker-compose logs app

# Check if port is available
docker ps -a

# Clean up and restart
docker-compose down -v
docker-compose up -d
```

### Authorization Issues
```bash
# Verify credentials
curl -u admin:admin123 http://localhost:8080/api/v1/students

# Check if Spring Security is enabled
# Look for "POST /login" in logs
```

---

## 📈 Performance Tips

### Database Optimization
```bash
# Add indexes (already configured in StudentEntity)
# Connection pooling (HikariCP configured)
# Pagination for large datasets
```

### API Best Practices
```bash
# Always use pagination for list endpoints
/api/v1/students?page=0&size=10

# Use filters to reduce data
/api/v1/students/search/gpa?minGpa=3.0&maxGpa=4.0

# Enable caching for frequently accessed data
```

---

## 🎓 Learning Resources

- **Spring Boot:** https://spring.io/projects/spring-boot
- **Spring Data JPA:** https://spring.io/projects/spring-data-jpa
- **OpenAPI/Swagger:** https://swagger.io/
- **Docker:** https://docs.docker.com/

---

## 📞 Support

For issues or questions:
1. Check logs: `docker-compose logs app`
2. Review `ENHANCEMENT_SUMMARY.md`
3. Consult `README_v2.md`
4. Check API docs at: `/swagger-ui.html`

---

**Happy Coding! 🚀**

For more details, see:
- [README_v2.md](README_v2.md) - Comprehensive documentation
- [ENHANCEMENT_SUMMARY.md](ENHANCEMENT_SUMMARY.md) - What's new
- [database/schema.sql](database/schema.sql) - Database schema
