# Student Management System - Spring Boot REST API & Web UI

![Version](https://img.shields.io/badge/version-2.0.0-blue)
![Java](https://img.shields.io/badge/java-17+-green)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.1-brightgreen)
![Maven](https://img.shields.io/badge/maven-3.9+-brightgreen)
![License](https://img.shields.io/badge/license-MIT-blue)

A professional-grade **Spring Boot REST API** with integrated **web UI** for comprehensive student management. Features advanced search, pagination, authentication, Docker containerization, and API documentation.

---

## 🎯 Quick Links

- [✨ Features](#-features)
- [🚀 Quick Start](#-quick-start)
- [📁 Project Structure](#-project-structure)
- [🏗️ Architecture](#-architecture)
- [🛠️ Technology Stack](#-technology-stack)
- [📦 Installation](#-installation)
- [⚙️ Configuration](#-configuration)
- [🎮 Usage](#-usage)
- [📚 API Documentation](#-api-documentation)
- [🧪 Testing](#-testing)
- [🐳 Docker Deployment](#-docker-deployment)
- [📖 Development Phases](#-development-phases)
- [📄 Documentation](#-documentation)

---

## ✨ Features

### Version 1.0 (Original)
- ✅ Console-based application
- ✅ In-memory ArrayList storage (Phase 1)
- ✅ MySQL database persistence (Phase 2)
- ✅ CRUD operations
- ✅ Search functionality
- ✅ Statistics reporting

### Version 2.0 (Enhanced)

#### REST API
- ✅ **RESTful Endpoints** - Full CRUD operations via HTTP
- ✅ **Advanced Search** - Search by name, email, status, GPA range
- ✅ **Pagination** - Efficient data handling with offset/limit
- ✅ **Filtering** - Multiple criteria filtering
- ✅ **Sorting** - Customizable result sorting
- ✅ **Swagger/OpenAPI** - Interactive API documentation

#### Web User Interface
- ✅ **Thymeleaf Templates** - Modern server-side rendering
- ✅ **Responsive Design** - Bootstrap 5 UI
- ✅ **Form Validation** - Client & server-side validation
- ✅ **Real-time Feedback** - Toast notifications
- ✅ **Dashboard** - System statistics and analytics

#### Authentication & Security
- ✅ **Spring Security** - Role-based access control
- ✅ **Login System** - User authentication
- ✅ **Password Encryption** - BCrypt hashing
- ✅ **CSRF Protection** - Token-based CSRF defense
- ✅ **SQL Injection Prevention** - Parameterized queries

#### Database Features
- ✅ **JPA/Hibernate ORM** - Object-relational mapping
- ✅ **MySQL 8.0** - Primary database
- ✅ **PostgreSQL Support** - Alternative database
- ✅ **Connection Pooling** - HikariCP for optimal performance
- ✅ **Migration Support** - Automatic schema updates
- ✅ **Audit Logging** - Track created/updated timestamps

#### Data Export/Import
- ✅ **CSV Export** - Download student data as CSV
- ✅ **CSV Import** - Bulk upload from CSV files
- ✅ **JSON API** - Export as JSON format
- ✅ **Batch Operations** - Process multiple records

#### DevOps & Deployment
- ✅ **Docker Support** - Multi-stage container build
- ✅ **Docker Compose** - Complete stack orchestration
- ✅ **CI/CD Pipeline** - GitHub Actions workflow
- ✅ **Health Checks** - Liveness & readiness probes
- ✅ **Logging** - SLF4J + Logback integration

#### Developer Experience
- ✅ **Comprehensive Documentation** - SRS, TDD, API docs
- ✅ **Unit & Integration Tests** - Full test coverage
- ✅ **Spring DevTools** - Hot reload support
- ✅ **Actuator** - Application metrics & monitoring
- ✅ **Error Handling** - Centralized exception management

---

## 🚀 Quick Start

### Prerequisites
- **Java 17+** (Java 21 LTS recommended)
- **Maven 3.9+**
- **MySQL 8.0+** or **PostgreSQL 12+**
- **Docker & Docker Compose** (optional)

### Option 1: Run Locally

```bash
# Clone the repository
git clone https://github.com/your-org/StudentManagementSystem.git
cd StudentManagementSystem

# Build the project
mvn clean package

# Run the Spring Boot application
mvn spring-boot:run

# Application will be available at:
# http://localhost:8080/sms
```

### Option 2: Run with Docker Compose

```bash
# Start all services (MySQL + Application)
docker-compose up -d

# Application will be available at:
# http://localhost:8080/sms

# PHPMyAdmin available at:
# http://localhost:8081
```

### Option 3: Run JAR File

```bash
# Build JAR
mvn clean package

# Run JAR
java -jar target/StudentManagementSystem-*.jar

# Access at: http://localhost:8080/sms
```

---

## 📁 Project Structure

```
StudentManagementSystem/
├── src/
│   ├── main/
│   │   ├── java/com/sms/
│   │   │   ├── SpringBootApp.java              # Main Spring Boot entry point
│   │   │   ├── controller/                     # REST API controllers
│   │   │   │   ├── StudentApiController.java   # REST endpoints
│   │   │   │   ├── ApiResponse.java            # Response wrapper
│   │   │   │   └── StudentStatistics.java      # Statistics DTO
│   │   │   ├── service/                        # Business logic
│   │   │   │   ├── StudentApiService.java      # REST API service
│   │   │   │   └── StudentService.java         # Console service
│   │   │   ├── repository/                     # Data access
│   │   │   │   └── StudentRepository.java      # JPA repository
│   │   │   ├── model/                          # Domain entities
│   │   │   │   ├── StudentEntity.java          # JPA entity
│   │   │   │   └── StudentStatus.java          # Enum
│   │   │   ├── dto/                            # Data transfer objects
│   │   │   │   └── StudentDTO.java             # API DTO
│   │   │   ├── exception/                      # Exception handling
│   │   │   │   ├── ResourceNotFoundException.java
│   │   │   │   └── GlobalExceptionHandler.java
│   │   │   └── util/                           # Utilities
│   │   ├── resources/
│   │   │   ├── application.properties          # Spring Boot config
│   │   │   ├── templates/                      # Thymeleaf templates
│   │   │   └── static/                         # CSS, JS, images
│   │   └── webapp/                             # Web content
│   └── test/                                   # Unit & integration tests
├── database/
│   └── schema.sql                              # Database schema
├── docs/
│   ├── SRS.md                                  # Software Requirements
│   ├── TDD_Phase1.md                           # Test-driven development
│   ├── TDD_Phase2.md
│   └── API.md                                  # API documentation
├── Dockerfile                                  # Container image
├── docker-compose.yml                          # Container orchestration
├── pom.xml                                     # Maven configuration
├── README.md                                   # This file
└── .github/workflows/ci-cd.yml                # GitHub Actions pipeline
```

---

## 🏗️ Architecture

### Layered Architecture
```
┌─────────────────────────────────────┐
│      REST API / Web UI Layer        │
│  (Controllers, Thymeleaf Views)     │
├─────────────────────────────────────┤
│     Business Logic Layer            │
│  (Services, Business Rules)         │
├─────────────────────────────────────┤
│     Data Access Layer               │
│  (Repository, JPA/Hibernate)        │
├─────────────────────────────────────┤
│     Database Layer                  │
│  (MySQL / PostgreSQL)               │
└─────────────────────────────────────┘
```

### Key Design Patterns

1. **DAO Pattern** - Data Access Object for abstraction
2. **Service Layer** - Business logic separation
3. **Repository Pattern** - Spring Data JPA
4. **DTO Pattern** - Data transfer between layers
5. **Exception Handling** - Centralized error management

---

## 🛠️ Technology Stack

### Backend
- **Java 17 LTS** - Programming language
- **Spring Boot 3.2.1** - Web framework
- **Spring Data JPA** - ORM framework
- **Hibernate** - Object-relational mapping
- **Spring Security** - Authentication & authorization
- **Thymeleaf** - Server-side templating

### Database
- **MySQL 8.0** - Primary database
- **PostgreSQL 12+** - Alternative database
- **HikariCP** - Connection pooling
- **Liquibase/Flyway** - Database migrations

### API Documentation
- **SpringDoc OpenAPI 2.0.2** - REST documentation
- **Swagger UI** - Interactive API explorer

### Build & Deployment
- **Maven 3.9+** - Build tool
- **Docker** - Containerization
- **Docker Compose** - Orchestration
- **GitHub Actions** - CI/CD pipeline

### Testing
- **JUnit 5** - Unit testing
- **Mockito** - Mocking framework
- **Spring Boot Test** - Integration testing

### Utilities
- **SLF4J + Logback** - Logging
- **Apache Commons CSV** - CSV processing
- **Validation API** - Input validation
- **Jackson** - JSON processing

---

## 📦 Installation

### Step 1: Clone Repository

```bash
git clone https://github.com/your-org/StudentManagementSystem.git
cd StudentManagementSystem
```

### Step 2: Configure Database

#### MySQL
```bash
# Create database
mysql -u root -p < database/schema.sql

# Update application.properties
spring.datasource.url=jdbc:mysql://localhost:3306/student_management_system
spring.datasource.username=root
spring.datasource.password=root
```

#### PostgreSQL (Alternative)
```bash
# Update application.properties
spring.datasource.url=jdbc:postgresql://localhost:5432/student_management_system
spring.datasource.username=postgres
spring.datasource.password=postgres
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQL10Dialect
```

### Step 3: Build & Run

```bash
# Build project
mvn clean package

# Run application
mvn spring-boot:run

# Or run JAR
java -jar target/StudentManagementSystem-2.0.0.jar
```

---

## ⚙️ Configuration

### application.properties

Key configuration options:

```properties
# Server
server.port=8080
spring.application.name=Student Management System

# Database
spring.datasource.url=jdbc:mysql://localhost:3306/student_management_system
spring.datasource.username=root
spring.datasource.password=root
spring.jpa.hibernate.ddl-auto=update

# Logging
logging.level.com.sms=DEBUG
logging.file.name=logs/application.log

# API Documentation
springdoc.swagger-ui.enabled=true

# Security
spring.security.user.name=admin
spring.security.user.password=admin123
```

### Environment Profiles

```bash
# Development
mvn spring-boot:run -Dspring.profiles.active=dev

# Production
mvn spring-boot:run -Dspring.profiles.active=prod
```

---

## 🎮 Usage

### Web UI

1. Navigate to: `http://localhost:8080/sms`
2. Login with credentials:
   - **Username:** admin
   - **Password:** admin123
3. Use the dashboard to:
   - Create new students
   - Search and filter records
   - Export data as CSV
   - View statistics

### REST API

#### Get All Students
```bash
curl -X GET http://localhost:8080/api/v1/students?page=0&size=10
```

#### Create Student
```bash
curl -X POST http://localhost:8080/api/v1/students \
  -H "Content-Type: application/json" \
  -d '{
    "name": "John Doe",
    "email": "john@example.com",
    "phone": "9876543210",
    "gpa": 3.8,
    "status": "ACTIVE"
  }'
```

#### Search by Name
```bash
curl -X GET "http://localhost:8080/api/v1/students/search/name?name=John&page=0&size=10"
```

#### Filter by GPA
```bash
curl -X GET "http://localhost:8080/api/v1/students/search/gpa?minGpa=3.0&maxGpa=4.0"
```

#### Get Statistics
```bash
curl -X GET http://localhost:8080/api/v1/students/statistics
```

---

## 📚 API Documentation

### Swagger UI
- URL: `http://localhost:8080/sms/swagger-ui.html`
- Provides interactive documentation for all endpoints

### API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/v1/students` | Get all students (paginated) |
| GET | `/api/v1/students/{id}` | Get student by ID |
| POST | `/api/v1/students` | Create new student |
| PUT | `/api/v1/students/{id}` | Update student |
| DELETE | `/api/v1/students/{id}` | Delete student |
| GET | `/api/v1/students/search/name?name=...` | Search by name |
| GET | `/api/v1/students/search/status?status=...` | Filter by status |
| GET | `/api/v1/students/search/gpa?minGpa=...&maxGpa=...` | Filter by GPA |
| GET | `/api/v1/students/statistics` | Get system statistics |

---

## 🧪 Testing

### Run Unit Tests
```bash
mvn test
```

### Run Integration Tests
```bash
mvn verify
```

### Generate Coverage Report
```bash
mvn clean test jacoco:report
```

### View Coverage Report
```bash
# Open target/site/jacoco/index.html in browser
```

---

## 🐳 Docker Deployment

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

### Docker Compose (Recommended)
```bash
# Start all services
docker-compose up -d

# View logs
docker-compose logs -f app

# Stop services
docker-compose down
```

### Docker Compose Services
- **MySQL** (Port 3306)
- **Spring Boot App** (Port 8080)
- **PHPMyAdmin** (Port 8081)

---

## 📖 Development Phases

### Phase 1: Core Console Application (Completed)
- ✅ In-memory student management
- ✅ Console UI
- ✅ Basic CRUD operations
- ✅ Validation & error handling

### Phase 2: Database Integration (Completed)
- ✅ JDBC implementation
- ✅ PreparedStatements
- ✅ Database persistence
- ✅ DAO pattern

### Phase 3: Spring Boot Migration (Completed)
- ✅ REST API endpoints
- ✅ JPA/Hibernate ORM
- ✅ Spring Data repositories
- ✅ API documentation

### Phase 4: Enhanced Features (Current)
- ✅ Advanced search & filtering
- ✅ Pagination support
- ✅ Web UI with Thymeleaf
- ✅ Authentication & authorization
- ✅ CSV import/export
- ✅ Docker containerization
- ✅ CI/CD pipeline

---

## 📄 Documentation

### Project Documentation
- [SRS.md](docs/SRS.md) - Software Requirements Specification
- [TDD_Phase1.md](docs/TDD_Phase1.md) - Test-driven development Phase 1
- [TDD_Phase2.md](docs/TDD_Phase2.md) - Test-driven development Phase 2
- [API.md](docs/API.md) - API reference documentation

### Database
- [schema.sql](database/schema.sql) - Database schema
- [setup-guide.md](SETUP_GUIDE.md) - Setup instructions

---

## 🤝 Contributing

1. Fork the repository
2. Create feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit changes (`git commit -m 'Add AmazingFeature'`)
4. Push to branch (`git push origin feature/AmazingFeature`)
5. Open Pull Request

### Development Guidelines
- Follow Java coding standards
- Write unit tests for new features
- Update documentation
- Run full test suite before submitting PR

---

## 📋 Troubleshooting

### Issue: Database Connection Failed

**Solution:**
```bash
# Verify MySQL is running
mysql -u root -p -e "SELECT 1"

# Check application.properties
cat src/main/resources/application.properties | grep datasource

# Update credentials if needed
```

### Issue: Port Already in Use

**Solution:**
```bash
# Change port in application.properties
server.port=8081

# Or kill existing process
lsof -ti:8080 | xargs kill -9
```

### Issue: Docker Compose Won't Start

**Solution:**
```bash
# Check logs
docker-compose logs

# Verify Docker daemon
docker ps

# Clean up and restart
docker-compose down -v
docker-compose up -d
```

---

## 📜 License

This project is licensed under the MIT License - see [LICENSE](LICENSE) file for details.

---

## 👥 Support

For issues, questions, or contributions, please:
- Open an issue on GitHub
- Contact: [your-email@example.com](mailto:your-email@example.com)
- Check FAQ in [WIKI](https://github.com/your-org/StudentManagementSystem/wiki)

---

**Last Updated:** December 2024  
**Version:** 2.0.0  
**Author:** SMS Development Team
