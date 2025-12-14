# Software Requirements Specification (SRS)
## Student Management System - Console Application

**Document Version:** 1.0  
**Date:** December 2024  
**Author:** SMS Development Team  
**Status:** Approved

---

## 1. Introduction

### 1.1 Purpose
This document outlines the software requirements for the Student Management System (SMS), a console-based application designed to manage student information with both in-memory and database persistence options.

### 1.2 Scope
The SMS provides comprehensive student record management capabilities including:
- Student registration and enrollment
- Full CRUD (Create, Read, Update, Delete) operations
- Student search and filtering
- System statistics and reporting
- Both in-memory (Phase 1) and MySQL database storage (Phase 2)

### 1.3 Definitions and Acronyms
- **DAO:** Data Access Object pattern
- **JDBC:** Java Database Connectivity
- **POJO:** Plain Old Java Object
- **SMS:** Student Management System
- **CRUD:** Create, Read, Update, Delete operations

---

## 2. Overall Description

### 2.1 Product Overview
The Student Management System is a Java-based console application that provides educational institutions with tools to:
- Register and manage student information
- Perform quick searches across student database
- Update student records and enrollment status
- Generate basic statistics about enrollment

### 2.2 User Classes and Characteristics
- **System Administrator:** Manages student records
- **Registrar Staff:** Adds and updates student information
- **Support Personnel:** Searches and retrieves student data

### 2.3 Operating Environment
- **Platform:** Windows/Linux/macOS
- **Java Version:** Java 17 or higher
- **Database (Phase 2):** MySQL 5.7+ or PostgreSQL 12+
- **IDE:** VS Code with Java Extensions
- **Build Tool:** Apache Maven 3.6+

### 2.4 Design and Implementation Constraints
- **Language:** Core Java (no frameworks in Phase 1)
- **Architecture:** Layered (Model → DAO → Service → UI)
- **Security:** PreparedStatement for SQL injection prevention
- **Concurrency:** Single-threaded console application

---

## 3. System Features

### 3.1 Feature 1: Student Registration (Create)
**ID:** F-001  
**Priority:** High  
**Description:** Add new student to the system

**Requirements:**
- Accept student information (first name, last name, email, phone)
- Validate input data (name format, email validity, phone number)
- Ensure email uniqueness
- Assign auto-generated Student ID
- Set default enrollment status (ACTIVE) and date
- Support optional fields (address, date of birth, etc.)

**Acceptance Criteria:**
- System prevents duplicate email registration
- All required fields are validated before saving
- Auto-generated ID is returned to user
- Confirmation message displays new student details

### 3.2 Feature 2: View All Students (Read)
**ID:** F-002  
**Priority:** High  
**Description:** Display list of all students in system

**Requirements:**
- Display all students in a formatted table
- Show ID, Name, Email, and Status
- Support empty result handling
- Display total student count

**Acceptance Criteria:**
- Data displays in clear, organized format
- All students are retrieved successfully
- Empty system is handled gracefully

### 3.3 Feature 3: Search Functionality (Search)
**ID:** F-003  
**Priority:** High  
**Description:** Search for specific students

**Requirements:**
- Search by Student ID
- Search by First Name (partial/full match)
- Search by Last Name (partial/full match)
- Search by Email (exact match)
- Display detailed student information
- Support no results scenario

**Acceptance Criteria:**
- Searches return accurate results
- Case-insensitive matching for names
- User can easily view full student details
- No results handled professionally

### 3.4 Feature 4: Update Student Information (Update)
**ID:** F-004  
**Priority:** High  
**Description:** Modify existing student records

**Requirements:**
- Accept student ID to locate record
- Allow updating all editable fields
- Prevent email conflicts
- Validate updated data
- Support partial updates (optional fields)
- Display updated confirmation

**Acceptance Criteria:**
- System prevents duplicate email changes
- Only valid data is accepted
- Update is confirmed successfully
- Validation prevents invalid data entry

### 3.5 Feature 5: Delete Student Records (Delete)
**ID:** F-005  
**Priority:** Medium  
**Description:** Remove student records from system

**Requirements:**
- Require student ID for deletion
- Confirm deletion before removal
- Prevent accidental deletions
- Handle non-existent students

**Acceptance Criteria:**
- Confirmation required before deletion
- System confirms successful deletion
- No data loss occurs from accidental deletion

### 3.6 Feature 6: Student Statistics
**ID:** F-006  
**Priority:** Medium  
**Description:** Display system statistics

**Requirements:**
- Show total student count
- Display active student count
- Display inactive student count

**Acceptance Criteria:**
- Statistics are accurate
- Numbers reflect current database state
- Display is clear and organized

---

## 4. Functional Requirements

### 4.1 User Interface Requirements
**UI-R1:** Console menu-driven interface  
**UI-R2:** Clear screen clearing between operations  
**UI-R3:** Input validation with error messages  
**UI-R4:** Confirmation prompts for destructive operations  
**UI-R5:** Formatted tabular output for lists  

### 4.2 Data Validation Requirements
**DV-R1:** First/Last Name: 2-50 characters, letters/spaces/hyphens/apostrophes  
**DV-R2:** Email: Valid format, maximum 100 characters  
**DV-R3:** Phone: Minimum 10 digits after removing non-numeric  
**DV-R4:** Email: Must be unique in system  
**DV-R5:** ID: Positive integer, system-generated  

### 4.3 Security Requirements
**SEC-R1:** Use PreparedStatement for all database queries  
**SEC-R2:** Prevent SQL injection attacks  
**SEC-R3:** Validate all user inputs  
**SEC-R4:** Secure database credential storage (application.properties)  
**SEC-R5:** Log all critical operations  

### 4.4 Data Persistence Requirements
**DP-R1:** Phase 1: In-memory storage using ArrayList  
**DP-R2:** Phase 2: MySQL database with JDBC  
**DP-R3:** Database connection using try-with-resources  
**DP-R4:** Auto-increment primary keys  

---

## 5. Non-Functional Requirements

### 5.1 Performance Requirements
**PERF-R1:** Application startup within 2 seconds  
**PERF-R2:** Search operations complete within 1 second  
**PERF-R3:** Display all students (500+) within 2 seconds  
**PERF-R4:** CRUD operations respond within 500ms  

### 5.2 Reliability Requirements
**REL-R1:** Application handles errors gracefully  
**REL-R2:** Automatic database reconnection on failure  
**REL-R3:** Data consistency with transactions  
**REL-R4:** Logging of all errors for troubleshooting  

### 5.3 Usability Requirements
**USAB-R1:** User-friendly menu navigation  
**USAB-R2:** Clear error messages with recovery options  
**USAB-R3:** Consistent interface across operations  
**USAB-R4:** Help options for confused users  

### 5.4 Maintainability Requirements
**MAINT-R1:** Code follows Java conventions  
**MAINT-R2:** JavaDoc documentation for all classes  
**MAINT-R3:** Clear separation of concerns (layers)  
**MAINT-R4:** Logging framework for debugging  

### 5.5 Scalability Requirements
**SCAL-R1:** Support for 10,000+ students  
**SCAL-R2:** Database indexing for performance  
**SCAL-R3:** Connection pooling capability  

---

## 6. Data Requirements

### 6.1 Student Data Model
```
Student
├── studentId (Long) - Unique identifier, auto-generated
├── firstName (String) - Required, 2-50 chars
├── lastName (String) - Required, 2-50 chars
├── email (String) - Required, unique, max 100 chars
├── phoneNumber (String) - Required, min 10 digits
├── dateOfBirth (LocalDate) - Optional
├── address (String) - Optional
├── city (String) - Optional
├── state (String) - Optional
├── zipCode (String) - Optional
├── enrollmentDate (LocalDate) - Required, defaults to today
└── enrollmentStatus (String) - Required, defaults to "ACTIVE"
```

### 6.2 Data Storage Options
- **Phase 1:** ArrayList in memory
- **Phase 2:** MySQL database with JDBC

### 6.3 Data Integrity Constraints
- Primary Key: studentId (unique, not null)
- Unique Constraint: email (case-insensitive)
- Foreign Keys: None in base version
- Check Constraints: enrollmentStatus in ('ACTIVE', 'INACTIVE', 'SUSPENDED')

---

## 7. External Interface Requirements

### 7.1 User Interface
- Console-based terminal application
- Menu-driven navigation
- Formatted table output
- Input prompts with validation

### 7.2 Database Interface
- JDBC connection to MySQL
- Prepared statements for all queries
- Connection pooling ready
- Error handling and logging

---

## 8. Architecture & Design Patterns

### 8.1 Layered Architecture
```
┌─────────────────────────────┐
│  UI Layer (ConsoleUI)       │
├─────────────────────────────┤
│  Service Layer              │
│  (StudentService)           │
├─────────────────────────────┤
│  DAO Layer                  │
│  (Interface + Impl)         │
├─────────────────────────────┤
│  Database Layer             │
│  (DatabaseUtil, JDBC)       │
└─────────────────────────────┘
```

### 8.2 Design Patterns
- **DAO Pattern:** Abstracts database access
- **Dependency Injection:** Loose coupling
- **Strategy Pattern:** Interchangeable DAO implementations
- **Template Method:** Common database operations

---

## 9. Quality Assurance Requirements

### 9.1 Testing Strategy
- **Unit Testing:** DAO and Service layer
- **Integration Testing:** End-to-end workflows
- **Validation Testing:** Input validation
- **Manual Testing:** Console UI interaction

### 9.2 Code Quality
- Minimum 80% code coverage
- Zero critical code smells
- All public APIs documented
- No hardcoded values or credentials

---

## 10. Deployment Requirements

### 10.1 Build and Packaging
- Maven project structure
- Fat JAR creation via maven-shade-plugin
- Runnable from command line

### 10.2 Installation
- Extract project files
- Configure application.properties
- Run Maven build: `mvn clean package`
- Execute: `java -jar StudentManagementSystem.jar`

---

## 11. Timeline and Deliverables

### Week 1: Setup & Analysis
- Project structure creation
- POJO model development
- Database schema design
- SRS documentation

### Week 2: In-Memory Implementation
- ArrayList-based DAO
- Service layer validation
- Console UI development
- Basic testing

### Week 3: Database Integration
- JDBC DAO implementation
- DatabaseUtil creation
- Database integration testing
- Documentation updates

### Week 4: Polish & Testing
- Comprehensive testing
- Logging implementation
- Final documentation
- Performance optimization

---

## 12. Assumptions and Constraints

### 12.1 Assumptions
- Java 17+ is installed on the system
- MySQL 5.7+ or PostgreSQL is available for Phase 2
- Single-user console application
- No network connectivity required for Phase 1

### 12.2 Constraints
- Single-threaded application
- Console-only interface (no GUI)
- Limited to local machine for Phase 1
- Memory constraints for very large datasets

---

## 13. Approval

| Role | Name | Date | Signature |
|------|------|------|-----------|
| Project Lead | Development Team | Dec 2024 | ✓ |
| QA Lead | QA Team | Dec 2024 | ✓ |
| Product Owner | Management | Dec 2024 | ✓ |

---

**End of SRS Document**
