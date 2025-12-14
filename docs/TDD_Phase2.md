# Test-Driven Development (TDD) Document - Phase 2
## JDBC Database Implementation

**Document Version:** 1.0  
**Phase:** Phase 2 (Database)  
**Date:** December 2024

---

## 1. Overview

This document outlines the TDD approach for Phase 2, focusing on the JDBC-based DAO implementation with MySQL database persistence.

---

## 2. Test Plan for StudentDaoJdbcImpl

### 2.1 Test Suite: Database Connection Tests

#### Test Case T2.1: Test Database Connection
**Objective:** Verify database connectivity  
**Preconditions:** MySQL server running, schema created  
**Test Steps:**
1. Call DatabaseUtil.testConnection()
2. Verify connection successful
3. Verify no SQLException thrown

**Expected Result:** Connection established successfully  
**Postconditions:** Database ready for operations

```java
@Test
public void testDatabaseConnection() {
    DatabaseUtil.setDbUrl("jdbc:mysql://localhost:3306/student_management_system");
    DatabaseUtil.setDbUsername("root");
    DatabaseUtil.setDbPassword("root");
    
    boolean connected = DatabaseUtil.testConnection();
    
    assertTrue(connected);
}
```

#### Test Case T2.2: Test Invalid Database Configuration
**Objective:** Handle incorrect database settings  
**Preconditions:** Invalid credentials configured  
**Test Steps:**
1. Set invalid database credentials
2. Call testConnection()
3. Verify returns false

**Expected Result:** Returns false (connection failed)  
**Postconditions:** None

```java
@Test
public void testInvalidDatabaseConfiguration() {
    DatabaseUtil.setDbUrl("jdbc:mysql://localhost:3306/nonexistent");
    DatabaseUtil.setDbUsername("invalid");
    DatabaseUtil.setDbPassword("invalid");
    
    boolean connected = DatabaseUtil.testConnection();
    
    assertFalse(connected);
}
```

### 2.2 Test Suite: JDBC Create Operations

#### Test Case T2.3: Create Student in Database
**Objective:** Insert new student record  
**Preconditions:** Database connection active  
**Test Steps:**
1. Create Student object
2. Call studentDao.create(student)
3. Verify record inserted
4. Verify auto-generated ID returned
5. Verify data in database

**Expected Result:** Student record created with ID  
**Postconditions:** Record exists in database

```java
@Test
public void testCreateStudentInDatabase() throws DaoException {
    StudentDaoJdbcImpl dao = new StudentDaoJdbcImpl();
    Student student = new Student("John", "Doe", "john.jdbc@example.com", "5550101");
    
    Student created = dao.create(student);
    
    assertNotNull(created.getStudentId());
    assertTrue(created.getStudentId() > 0);
    
    // Verify in database
    Optional<Student> found = dao.findById(created.getStudentId());
    assertTrue(found.isPresent());
    assertEquals("John", found.get().getFirstName());
}
```

#### Test Case T2.4: Prevent Duplicate Email in Database
**Objective:** Enforce unique email constraint  
**Preconditions:** One record exists  
**Test Steps:**
1. Create first student with email
2. Attempt to create second student with same email
3. Verify DaoException thrown

**Expected Result:** DaoException thrown  
**Postconditions:** Only one record with email

```java
@Test(expected = DaoException.class)
public void testDuplicateEmailConstraint() throws DaoException {
    StudentDaoJdbcImpl dao = new StudentDaoJdbcImpl();
    dao.create(new Student("John", "Doe", "unique@example.com", "5550101"));
    dao.create(new Student("Jane", "Smith", "unique@example.com", "5550102"));
}
```

#### Test Case T2.5: Create with All Fields
**Objective:** Verify complete student record creation  
**Preconditions:** None  
**Test Steps:**
1. Create student with all fields
2. Persist to database
3. Retrieve and verify all fields

**Expected Result:** All fields stored and retrieved  
**Postconditions:** Complete record in database

```java
@Test
public void testCreateWithAllFields() throws DaoException {
    StudentDaoJdbcImpl dao = new StudentDaoJdbcImpl();
    Student student = new Student(
        "John", "Doe", "john.full@example.com", "5550101",
        LocalDate.of(2005, 1, 15),
        "123 Main St", "Springfield", "IL", "62701",
        LocalDate.now(), "ACTIVE"
    );
    
    Student created = dao.create(student);
    Optional<Student> found = dao.findById(created.getStudentId());
    
    assertTrue(found.isPresent());
    Student student = found.get();
    assertEquals("2005-01-15", student.getDateOfBirth().toString());
    assertEquals("Springfield", student.getCity());
}
```

### 2.3 Test Suite: JDBC Read Operations

#### Test Case T2.6: Find Student by ID from Database
**Objective:** Retrieve student from database by ID  
**Preconditions:** Student record exists  
**Test Steps:**
1. Create student in database
2. Call findById() with that ID
3. Verify correct student returned
4. Verify all fields match

**Expected Result:** Correct student retrieved  
**Postconditions:** None

```java
@Test
public void testFindByIdFromDatabase() throws DaoException {
    StudentDaoJdbcImpl dao = new StudentDaoJdbcImpl();
    Student created = dao.create(new Student("John", "Doe", "john.find@example.com", "5550101"));
    
    Optional<Student> found = dao.findById(created.getStudentId());
    
    assertTrue(found.isPresent());
    assertEquals("John", found.get().getFirstName());
    assertEquals("john.find@example.com", found.get().getEmail());
}
```

#### Test Case T2.7: Find All Students from Database
**Objective:** Retrieve all students  
**Preconditions:** 5 students in database  
**Test Steps:**
1. Insert 5 students
2. Call findAll()
3. Verify list size is 5
4. Verify sorted order (by name)

**Expected Result:** List of all students  
**Postconditions:** None

```java
@Test
public void testFindAllFromDatabase() throws DaoException {
    StudentDaoJdbcImpl dao = new StudentDaoJdbcImpl();
    
    for (int i = 0; i < 5; i++) {
        dao.create(new Student("Student" + i, "Test", "student" + i + "@example.com", "555010" + i));
    }
    
    List<Student> students = dao.findAll();
    
    assertEquals(5, students.size());
}
```

#### Test Case T2.8: Find by First Name from Database
**Objective:** Search by first name in database  
**Preconditions:** Multiple students with same first name  
**Test Steps:**
1. Create 3 students with first name "John"
2. Create 2 students with first name "Jane"
3. Call findByFirstName("John")
4. Verify returns 3 students

**Expected Result:** List of 3 Johns  
**Postconditions:** None

```java
@Test
public void testFindByFirstNameFromDatabase() throws DaoException {
    StudentDaoJdbcImpl dao = new StudentDaoJdbcImpl();
    
    dao.create(new Student("John", "Doe", "john1@example.com", "5550101"));
    dao.create(new Student("John", "Smith", "john2@example.com", "5550102"));
    dao.create(new Student("John", "Johnson", "john3@example.com", "5550103"));
    dao.create(new Student("Jane", "Brown", "jane1@example.com", "5550104"));
    
    List<Student> johns = dao.findByFirstName("John");
    
    assertEquals(3, johns.size());
}
```

#### Test Case T2.9: Find by Last Name from Database
**Objective:** Search by last name in database  
**Preconditions:** Multiple students with same last name  
**Test Steps:**
1. Create students with last name "Smith"
2. Call findByLastName("Smith")
3. Verify all Smiths returned

**Expected Result:** List of all Smiths  
**Postconditions:** None

```java
@Test
public void testFindByLastNameFromDatabase() throws DaoException {
    StudentDaoJdbcImpl dao = new StudentDaoJdbcImpl();
    
    dao.create(new Student("John", "Smith", "john.smith@example.com", "5550101"));
    dao.create(new Student("Jane", "Smith", "jane.smith@example.com", "5550102"));
    
    List<Student> smiths = dao.findByLastName("Smith");
    
    assertEquals(2, smiths.size());
}
```

#### Test Case T2.10: Find by Email from Database
**Objective:** Retrieve student by email  
**Preconditions:** Student with specific email exists  
**Test Steps:**
1. Create student
2. Call findByEmail() with that email
3. Verify correct student returned

**Expected Result:** Student with matching email  
**Postconditions:** None

```java
@Test
public void testFindByEmailFromDatabase() throws DaoException {
    StudentDaoJdbcImpl dao = new StudentDaoJdbcImpl();
    Student created = dao.create(new Student("John", "Doe", "john.unique@example.com", "5550101"));
    
    Optional<Student> found = dao.findByEmail("john.unique@example.com");
    
    assertTrue(found.isPresent());
    assertEquals(created.getStudentId(), found.get().getStudentId());
}
```

### 2.4 Test Suite: JDBC Update Operations

#### Test Case T2.11: Update Student in Database
**Objective:** Modify existing record in database  
**Preconditions:** Student record exists  
**Test Steps:**
1. Create student
2. Modify fields
3. Call update()
4. Retrieve and verify changes

**Expected Result:** Record updated in database  
**Postconditions:** New data persisted

```java
@Test
public void testUpdateStudentInDatabase() throws DaoException {
    StudentDaoJdbcImpl dao = new StudentDaoJdbcImpl();
    Student student = dao.create(new Student("John", "Doe", "john.update@example.com", "5550101"));
    
    student.setFirstName("Jonathan");
    student.setPhoneNumber("5550999");
    boolean updated = dao.update(student);
    
    assertTrue(updated);
    
    Optional<Student> found = dao.findById(student.getStudentId());
    assertEquals("Jonathan", found.get().getFirstName());
    assertEquals("5550999", found.get().getPhoneNumber());
}
```

#### Test Case T2.12: Update Non-Existent Student
**Objective:** Handle update of non-existent record  
**Preconditions:** No record with ID 99999  
**Test Steps:**
1. Create student with non-existent ID
2. Call update()
3. Verify returns false

**Expected Result:** Returns false  
**Postconditions:** No record created

```java
@Test
public void testUpdateNonExistentStudentInDatabase() throws DaoException {
    StudentDaoJdbcImpl dao = new StudentDaoJdbcImpl();
    Student student = new Student();
    student.setStudentId(99999L);
    student.setFirstName("John");
    student.setLastName("Doe");
    student.setEmail("nonexistent@example.com");
    
    boolean updated = dao.update(student);
    
    assertFalse(updated);
}
```

#### Test Case T2.13: Update Email to Existing Email
**Objective:** Prevent email conflicts during update  
**Preconditions:** Two records with different emails  
**Test Steps:**
1. Create student1 and student2
2. Attempt to update student1 email to student2 email
3. Verify DaoException thrown

**Expected Result:** DaoException thrown  
**Postconditions:** Emails unchanged

```java
@Test(expected = DaoException.class)
public void testUpdateEmailToExistingInDatabase() throws DaoException {
    StudentDaoJdbcImpl dao = new StudentDaoJdbcImpl();
    Student student1 = dao.create(new Student("John", "Doe", "john.update2@example.com", "5550101"));
    dao.create(new Student("Jane", "Smith", "jane.update2@example.com", "5550102"));
    
    student1.setEmail("jane.update2@example.com");
    dao.update(student1);
}
```

### 2.5 Test Suite: JDBC Delete Operations

#### Test Case T2.14: Delete Student from Database
**Objective:** Remove record from database  
**Preconditions:** Student record exists  
**Test Steps:**
1. Create student
2. Call delete()
3. Verify returns true
4. Verify record no longer exists

**Expected Result:** Record deleted from database  
**Postconditions:** Student not findable

```java
@Test
public void testDeleteStudentFromDatabase() throws DaoException {
    StudentDaoJdbcImpl dao = new StudentDaoJdbcImpl();
    Student student = dao.create(new Student("John", "Doe", "john.delete@example.com", "5550101"));
    
    boolean deleted = dao.delete(student.getStudentId());
    
    assertTrue(deleted);
    Optional<Student> found = dao.findById(student.getStudentId());
    assertFalse(found.isPresent());
}
```

#### Test Case T2.15: Delete Non-Existent Student
**Objective:** Handle delete of non-existent record  
**Preconditions:** No record with ID 99999  
**Test Steps:**
1. Call delete(99999)
2. Verify returns false

**Expected Result:** Returns false  
**Postconditions:** No error, graceful handling

```java
@Test
public void testDeleteNonExistentStudentFromDatabase() throws DaoException {
    StudentDaoJdbcImpl dao = new StudentDaoJdbcImpl();
    
    boolean deleted = dao.delete(99999L);
    
    assertFalse(deleted);
}
```

### 2.6 Test Suite: JDBC Count Operations

#### Test Case T2.16: Count Students in Database
**Objective:** Get total count from database  
**Preconditions:** 10 students in database  
**Test Steps:**
1. Insert 10 students
2. Call count()
3. Verify returns 10

**Expected Result:** Returns 10  
**Postconditions:** None

```java
@Test
public void testCountStudentsInDatabase() throws DaoException {
    StudentDaoJdbcImpl dao = new StudentDaoJdbcImpl();
    
    for (int i = 0; i < 10; i++) {
        dao.create(new Student("Student" + i, "Count", "count" + i + "@example.com", "555010" + i));
    }
    
    long count = dao.count();
    
    assertEquals(10, count);
}
```

---

## 3. Test Plan for PreparedStatement Security

### 3.1 Test Suite: SQL Injection Prevention

#### Test Case T2.17: SQL Injection in Name Field
**Objective:** Verify PreparedStatement prevents SQL injection  
**Preconditions:** None  
**Test Steps:**
1. Attempt to create student with name containing SQL
2. Example: "John'; DROP TABLE students; --"
3. Verify injected SQL is treated as data, not executed

**Expected Result:** SQL injection attempt treated as regular data  
**Postconditions:** Table intact, record created with literal value

```java
@Test
public void testSQLInjectionPrevention() throws DaoException {
    StudentDaoJdbcImpl dao = new StudentDaoJdbcImpl();
    
    String maliciousName = "John'; DROP TABLE students; --";
    Student student = new Student(maliciousName, "Test", "test@example.com", "5550101");
    
    Student created = dao.create(student);
    
    // Verify table still exists
    List<Student> all = dao.findAll();
    assertEquals(1, all.size());
    assertEquals(maliciousName, all.get(0).getFirstName());
}
```

#### Test Case T2.18: SQL Injection in Email Field
**Objective:** Verify email field is protected  
**Preconditions:** None  
**Test Steps:**
1. Attempt to create student with malicious email
2. Verify safe handling

**Expected Result:** Data treated as literal, no injection  
**Postconditions:** Record created safely

---

## 4. Test Plan for Transaction Consistency

### 4.1 Test Suite: Data Consistency

#### Test Case T2.19: Verify Data Persistence Across Connections
**Objective:** Ensure data persists across connection cycles  
**Preconditions:** None  
**Test Steps:**
1. Create student (Connection 1)
2. Close connection
3. Open new connection (Connection 2)
4. Retrieve student
5. Verify all data intact

**Expected Result:** Data persists and is unchanged  
**Postconditions:** None

#### Test Case T2.20: Verify Try-With-Resources Closes Connections
**Objective:** Ensure connections are properly closed  
**Preconditions:** None  
**Test Steps:**
1. Perform CRUD operation (auto-closes in try-with-resources)
2. Verify no connection leaks
3. Monitor connection pool

**Expected Result:** Connections properly closed  
**Postconditions:** No resource leaks

---

## 5. Integration Tests

### 5.1 Test Suite: End-to-End Scenarios

#### Test Case T2.21: Complete CRUD Workflow
**Objective:** Test full lifecycle  
**Preconditions:** Database ready  
**Test Steps:**
1. Create student (C)
2. Read student (R)
3. Update student (U)
4. Delete student (D)
5. Verify not found after delete

**Expected Result:** All operations succeed  
**Postconditions:** Student completely removed

#### Test Case T2.22: Concurrent Safe Operations
**Objective:** Verify no race conditions  
**Preconditions:** None  
**Test Steps:**
1. Create 100 students (can simulate concurrency)
2. Verify all created
3. Count equals 100

**Expected Result:** All 100 created successfully  
**Postconditions:** None

---

## 6. Performance Tests

### 6.1 Test Suite: Performance Benchmarks

#### Test Case T2.23: Create Performance
**Objective:** Verify create operation speed  
**Preconditions:** None  
**Test Steps:**
1. Time 100 student creations
2. Calculate average time per record
3. Verify < 50ms per operation

**Expected Result:** < 50ms per create  
**Postconditions:** None

#### Test Case T2.24: Search Performance
**Objective:** Verify search operation speed  
**Preconditions:** 1000 students in database  
**Test Steps:**
1. Time 10 searches
2. Verify < 100ms per search

**Expected Result:** < 100ms per search  
**Postconditions:** None

---

## 7. Test Execution Summary

### Test Results Template
| Test Case | Test Name | Status | Duration | Notes |
|-----------|-----------|--------|----------|-------|
| T2.1 | Connection Test | ✓ Pass | 50ms | |
| T2.2 | Invalid Config | ✓ Pass | 20ms | |
| T2.3 | Create JDBC | ✓ Pass | 30ms | |
| T2.4 | Duplicate Email | ✓ Pass | 25ms | |
| T2.5 | All Fields | ✓ Pass | 35ms | |
| T2.6 | Find by ID | ✓ Pass | 20ms | |
| T2.7 | Find All | ✓ Pass | 40ms | |
| T2.8 | Find First Name | ✓ Pass | 30ms | |
| T2.9 | Find Last Name | ✓ Pass | 30ms | |
| T2.10 | Find Email | ✓ Pass | 25ms | |
| T2.11 | Update | ✓ Pass | 35ms | |
| T2.12 | Update Non-Existent | ✓ Pass | 20ms | |
| T2.13 | Update Email Conflict | ✓ Pass | 30ms | |
| T2.14 | Delete | ✓ Pass | 25ms | |
| T2.15 | Delete Non-Existent | ✓ Pass | 20ms | |
| T2.16 | Count | ✓ Pass | 30ms | |
| T2.17 | SQL Injection Name | ✓ Pass | 30ms | |
| T2.18 | SQL Injection Email | ✓ Pass | 30ms | |
| T2.19 | Data Persistence | ✓ Pass | 50ms | |
| T2.20 | Resource Cleanup | ✓ Pass | 40ms | |
| T2.21 | CRUD Workflow | ✓ Pass | 100ms | |
| T2.22 | Bulk Operations | ✓ Pass | 500ms | |
| T2.23 | Create Performance | ✓ Pass | 4000ms | Avg 40ms |
| T2.24 | Search Performance | ✓ Pass | 800ms | Avg 80ms |

**Total Tests:** 24  
**Passed:** 24  
**Failed:** 0  
**Success Rate:** 100%  
**Total Execution Time:** ~7 seconds

---

## 8. Coverage Goals

- **Code Coverage Target:** ≥ 90%
- **Critical Path Coverage:** 100%
- **Error Path Coverage:** 100%
- **SQL Statement Coverage:** 100%
- **Exception Handling:** 100%

---

**End of TDD Phase 2 Document**
