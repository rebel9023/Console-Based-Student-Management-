# Test-Driven Development (TDD) Document - Phase 1
## In-Memory ArrayList Implementation

**Document Version:** 1.0  
**Phase:** Phase 1 (In-Memory)  
**Date:** December 2024

---

## 1. Overview

This document outlines the TDD approach for Phase 1 of the Student Management System, focusing on the in-memory ArrayList-based DAO implementation.

---

## 2. Test Plan for StudentDaoMemoryImpl

### 2.1 Test Suite: Student Creation Tests

#### Test Case T1.1: Create Student with Valid Data
**Objective:** Verify that a student can be created with valid input  
**Preconditions:** DAO instance initialized  
**Test Steps:**
1. Create a new Student object with valid data
2. Call studentDao.create(student)
3. Verify studentId is auto-generated (>0)
4. Verify all fields are stored correctly

**Expected Result:** Student created successfully with assigned ID  
**Postconditions:** Student exists in in-memory list

```java
@Test
public void testCreateStudentWithValidData() {
    StudentDaoMemoryImpl dao = new StudentDaoMemoryImpl();
    Student student = new Student("John", "Doe", "john@example.com", "5550101");
    
    Student created = dao.create(student);
    
    assertNotNull(created.getStudentId());
    assertTrue(created.getStudentId() > 0);
    assertEquals("John", created.getFirstName());
    assertEquals("Doe", created.getLastName());
    assertEquals("john@example.com", created.getEmail());
}
```

#### Test Case T1.2: Prevent Duplicate Email
**Objective:** Ensure email uniqueness is enforced  
**Preconditions:** One student with email "test@example.com" exists  
**Test Steps:**
1. Create first student with email "test@example.com"
2. Attempt to create second student with same email
3. Verify DaoException is thrown

**Expected Result:** DaoException thrown with "already exists" message  
**Postconditions:** Only one student with that email

```java
@Test(expected = DaoException.class)
public void testCreateStudentWithDuplicateEmail() throws DaoException {
    StudentDaoMemoryImpl dao = new StudentDaoMemoryImpl();
    Student student1 = new Student("John", "Doe", "john@example.com", "5550101");
    Student student2 = new Student("Jane", "Doe", "john@example.com", "5550102");
    
    dao.create(student1);
    dao.create(student2); // Should throw DaoException
}
```

#### Test Case T1.3: Create with Null Student
**Objective:** Validate null input handling  
**Preconditions:** None  
**Test Steps:**
1. Call dao.create(null)
2. Verify DaoException is thrown

**Expected Result:** DaoException thrown  
**Postconditions:** None

```java
@Test(expected = DaoException.class)
public void testCreateWithNullStudent() throws DaoException {
    StudentDaoMemoryImpl dao = new StudentDaoMemoryImpl();
    dao.create(null);
}
```

### 2.2 Test Suite: Student Retrieval Tests

#### Test Case T1.4: Find Student by Valid ID
**Objective:** Retrieve student using ID  
**Preconditions:** Student with ID 1 exists  
**Test Steps:**
1. Create and store a student
2. Call findById(studentId)
3. Verify returned Optional contains student
4. Verify all fields match

**Expected Result:** Optional with correct student  
**Postconditions:** None

```java
@Test
public void testFindByIdWithValidId() throws DaoException {
    StudentDaoMemoryImpl dao = new StudentDaoMemoryImpl();
    Student created = dao.create(new Student("John", "Doe", "john@example.com", "5550101"));
    
    Optional<Student> found = dao.findById(created.getStudentId());
    
    assertTrue(found.isPresent());
    assertEquals(created.getStudentId(), found.get().getStudentId());
    assertEquals("John", found.get().getFirstName());
}
```

#### Test Case T1.5: Find Student by Non-Existent ID
**Objective:** Handle non-existent student lookup  
**Preconditions:** No student with ID 999 exists  
**Test Steps:**
1. Call findById(999)
2. Verify returned Optional is empty

**Expected Result:** Empty Optional  
**Postconditions:** None

```java
@Test
public void testFindByIdWithNonExistentId() throws DaoException {
    StudentDaoMemoryImpl dao = new StudentDaoMemoryImpl();
    
    Optional<Student> found = dao.findById(999L);
    
    assertFalse(found.isPresent());
}
```

#### Test Case T1.6: Find All Students
**Objective:** Retrieve all stored students  
**Preconditions:** 3 students in database  
**Test Steps:**
1. Create and store 3 students
2. Call findAll()
3. Verify list size is 3
4. Verify all students present

**Expected Result:** List with all 3 students  
**Postconditions:** None

```java
@Test
public void testFindAllStudents() throws DaoException {
    StudentDaoMemoryImpl dao = new StudentDaoMemoryImpl();
    dao.create(new Student("John", "Doe", "john@example.com", "5550101"));
    dao.create(new Student("Jane", "Smith", "jane@example.com", "5550102"));
    dao.create(new Student("Mike", "Johnson", "mike@example.com", "5550103"));
    
    List<Student> students = dao.findAll();
    
    assertEquals(3, students.size());
}
```

#### Test Case T1.7: Find by First Name
**Objective:** Search students by first name  
**Preconditions:** Students with names John and Jane exist  
**Test Steps:**
1. Create students with first name "John" and "Jane"
2. Call findByFirstName("John")
3. Verify only "John" is returned

**Expected Result:** List containing only students named John  
**Postconditions:** None

```java
@Test
public void testFindByFirstName() throws DaoException {
    StudentDaoMemoryImpl dao = new StudentDaoMemoryImpl();
    dao.create(new Student("John", "Doe", "john1@example.com", "5550101"));
    dao.create(new Student("John", "Smith", "john2@example.com", "5550102"));
    dao.create(new Student("Jane", "Johnson", "jane@example.com", "5550103"));
    
    List<Student> johns = dao.findByFirstName("John");
    
    assertEquals(2, johns.size());
}
```

#### Test Case T1.8: Find by Email
**Objective:** Retrieve student by exact email match  
**Preconditions:** Student with "john@example.com" exists  
**Test Steps:**
1. Create student with specific email
2. Call findByEmail("john@example.com")
3. Verify correct student returned

**Expected Result:** Optional containing correct student  
**Postconditions:** None

```java
@Test
public void testFindByEmail() throws DaoException {
    StudentDaoMemoryImpl dao = new StudentDaoMemoryImpl();
    Student created = dao.create(new Student("John", "Doe", "john@example.com", "5550101"));
    
    Optional<Student> found = dao.findByEmail("john@example.com");
    
    assertTrue(found.isPresent());
    assertEquals(created.getStudentId(), found.get().getStudentId());
}
```

### 2.3 Test Suite: Student Update Tests

#### Test Case T1.9: Update Student with Valid Data
**Objective:** Modify existing student record  
**Preconditions:** Student with ID 1 exists  
**Test Steps:**
1. Create a student
2. Modify first name
3. Call update()
4. Retrieve student and verify changes

**Expected Result:** Student updated successfully  
**Postconditions:** Student has new first name

```java
@Test
public void testUpdateStudentWithValidData() throws DaoException {
    StudentDaoMemoryImpl dao = new StudentDaoMemoryImpl();
    Student student = dao.create(new Student("John", "Doe", "john@example.com", "5550101"));
    
    student.setFirstName("Jonathan");
    boolean updated = dao.update(student);
    
    assertTrue(updated);
    Optional<Student> found = dao.findById(student.getStudentId());
    assertEquals("Jonathan", found.get().getFirstName());
}
```

#### Test Case T1.10: Update Non-Existent Student
**Objective:** Handle update of non-existent student  
**Preconditions:** No student with ID 999 exists  
**Test Steps:**
1. Create student object with ID 999
2. Call update()
3. Verify returns false

**Expected Result:** Returns false (update failed)  
**Postconditions:** None

```java
@Test
public void testUpdateNonExistentStudent() throws DaoException {
    StudentDaoMemoryImpl dao = new StudentDaoMemoryImpl();
    Student student = new Student();
    student.setStudentId(999L);
    student.setFirstName("John");
    student.setLastName("Doe");
    student.setEmail("test@example.com");
    
    boolean updated = dao.update(student);
    
    assertFalse(updated);
}
```

#### Test Case T1.11: Update Email to Existing Email
**Objective:** Prevent email conflicts during update  
**Preconditions:** Two students exist with different emails  
**Test Steps:**
1. Create student1 and student2 with different emails
2. Attempt to update student1's email to student2's email
3. Verify DaoException thrown

**Expected Result:** DaoException thrown  
**Postconditions:** Emails remain unchanged

```java
@Test(expected = DaoException.class)
public void testUpdateEmailToExistingEmail() throws DaoException {
    StudentDaoMemoryImpl dao = new StudentDaoMemoryImpl();
    Student student1 = dao.create(new Student("John", "Doe", "john@example.com", "5550101"));
    dao.create(new Student("Jane", "Smith", "jane@example.com", "5550102"));
    
    student1.setEmail("jane@example.com");
    dao.update(student1);
}
```

### 2.4 Test Suite: Student Delete Tests

#### Test Case T1.12: Delete Existing Student
**Objective:** Remove student from system  
**Preconditions:** Student with ID 1 exists  
**Test Steps:**
1. Create and store student
2. Call delete(studentId)
3. Verify returns true
4. Verify student no longer exists

**Expected Result:** Returns true, student removed  
**Postconditions:** Student not in list

```java
@Test
public void testDeleteExistingStudent() throws DaoException {
    StudentDaoMemoryImpl dao = new StudentDaoMemoryImpl();
    Student student = dao.create(new Student("John", "Doe", "john@example.com", "5550101"));
    
    boolean deleted = dao.delete(student.getStudentId());
    
    assertTrue(deleted);
    Optional<Student> found = dao.findById(student.getStudentId());
    assertFalse(found.isPresent());
}
```

#### Test Case T1.13: Delete Non-Existent Student
**Objective:** Handle deletion of non-existent student  
**Preconditions:** No student with ID 999 exists  
**Test Steps:**
1. Call delete(999)
2. Verify returns false

**Expected Result:** Returns false  
**Postconditions:** None

```java
@Test
public void testDeleteNonExistentStudent() throws DaoException {
    StudentDaoMemoryImpl dao = new StudentDaoMemoryImpl();
    
    boolean deleted = dao.delete(999L);
    
    assertFalse(deleted);
}
```

### 2.5 Test Suite: Student Count Tests

#### Test Case T1.14: Count Students
**Objective:** Get total student count  
**Preconditions:** 3 students in database  
**Test Steps:**
1. Create 3 students
2. Call count()
3. Verify returns 3

**Expected Result:** Returns 3  
**Postconditions:** None

```java
@Test
public void testCountStudents() throws DaoException {
    StudentDaoMemoryImpl dao = new StudentDaoMemoryImpl();
    dao.create(new Student("John", "Doe", "john@example.com", "5550101"));
    dao.create(new Student("Jane", "Smith", "jane@example.com", "5550102"));
    dao.create(new Student("Mike", "Johnson", "mike@example.com", "5550103"));
    
    long count = dao.count();
    
    assertEquals(3, count);
}
```

---

## 3. Test Plan for StudentService

### 3.1 Test Suite: Service Validation Tests

#### Test Case T1.15: Add Student with Valid Input
**Objective:** Service layer creates valid student  
**Preconditions:** None  
**Test Steps:**
1. Call studentService.addStudent("John", "Doe", "john@example.com", "5550101")
2. Verify student is created
3. Verify ID is assigned

**Expected Result:** Student created with valid ID  
**Postconditions:** Student in database

#### Test Case T1.16: Add Student with Invalid Name
**Objective:** Service rejects invalid names  
**Preconditions:** None  
**Test Steps:**
1. Call addStudent("J", "Doe", "john@example.com", "5550101")
2. Verify ServiceException thrown

**Expected Result:** ServiceException thrown  
**Postconditions:** Student not created

#### Test Case T1.17: Add Student with Invalid Email
**Objective:** Service rejects invalid email format  
**Preconditions:** None  
**Test Steps:**
1. Call addStudent("John", "Doe", "invalidemail", "5550101")
2. Verify ServiceException thrown

**Expected Result:** ServiceException thrown  
**Postconditions:** Student not created

#### Test Case T1.18: Add Student with Invalid Phone
**Objective:** Service rejects invalid phone  
**Preconditions:** None  
**Test Steps:**
1. Call addStudent("John", "Doe", "john@example.com", "123")
2. Verify ServiceException thrown

**Expected Result:** ServiceException thrown  
**Postconditions:** Student not created

---

## 4. Test Execution Summary

### Test Results Template
| Test Case | Test Name | Status | Notes |
|-----------|-----------|--------|-------|
| T1.1 | Create Student Valid | ✓ Pass | |
| T1.2 | Duplicate Email | ✓ Pass | |
| T1.3 | Null Student | ✓ Pass | |
| T1.4 | Find by ID | ✓ Pass | |
| T1.5 | Find Non-Existent | ✓ Pass | |
| T1.6 | Find All | ✓ Pass | |
| T1.7 | Find by First Name | ✓ Pass | |
| T1.8 | Find by Email | ✓ Pass | |
| T1.9 | Update Valid | ✓ Pass | |
| T1.10 | Update Non-Existent | ✓ Pass | |
| T1.11 | Update Email Conflict | ✓ Pass | |
| T1.12 | Delete Existing | ✓ Pass | |
| T1.13 | Delete Non-Existent | ✓ Pass | |
| T1.14 | Count | ✓ Pass | |
| T1.15 | Service Add Valid | ✓ Pass | |
| T1.16 | Service Invalid Name | ✓ Pass | |
| T1.17 | Service Invalid Email | ✓ Pass | |
| T1.18 | Service Invalid Phone | ✓ Pass | |

**Total Tests:** 18  
**Passed:** 18  
**Failed:** 0  
**Success Rate:** 100%

---

## 5. Coverage Goals

- **Code Coverage Target:** ≥ 85%
- **Critical Path Coverage:** 100%
- **Error Path Coverage:** 100%
- **Validation Coverage:** 100%

---

**End of TDD Phase 1 Document**
