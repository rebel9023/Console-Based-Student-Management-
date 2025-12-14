package com.sms.dao;

import com.sms.model.Student;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

/**
 * Unit test class for StudentDaoMemoryImpl.
 * Tests the in-memory ArrayList-based implementation.
 * 
 * @author SMS Development Team
 * @version 1.0
 * @since 2024
 */
public class StudentDaoMemoryImplTest {

    private StudentDaoMemoryImpl dao;

    @Before
    public void setUp() {
        dao = new StudentDaoMemoryImpl();
    }

    @After
    public void tearDown() {
        dao.clear();
    }

    // ==================== CREATE TESTS ====================

    @Test
    public void testCreateStudentWithValidData() throws DaoException {
        Student student = new Student("John", "Doe", "john@example.com", "5550101");

        Student created = dao.create(student);

        assertNotNull(created.getStudentId());
        assertTrue(created.getStudentId() > 0);
        assertEquals("John", created.getFirstName());
        assertEquals("john@example.com", created.getEmail());
        assertEquals("ACTIVE", created.getEnrollmentStatus());
    }

    @Test(expected = DaoException.class)
    public void testCreateWithDuplicateEmail() throws DaoException {
        Student student1 = new Student("John", "Doe", "john@example.com", "5550101");
        Student student2 = new Student("Jane", "Doe", "john@example.com", "5550102");

        dao.create(student1);
        dao.create(student2); // Should throw DaoException
    }

    @Test(expected = DaoException.class)
    public void testCreateWithNullStudent() throws DaoException {
        dao.create(null);
    }

    // ==================== READ TESTS ====================

    @Test
    public void testFindByIdWithValidId() throws DaoException {
        Student student = new Student("John", "Doe", "john@example.com", "5550101");
        Student created = dao.create(student);

        Optional<Student> found = dao.findById(created.getStudentId());

        assertTrue(found.isPresent());
        assertEquals(created.getStudentId(), found.get().getStudentId());
        assertEquals("John", found.get().getFirstName());
    }

    @Test
    public void testFindByIdWithNonExistentId() throws DaoException {
        Optional<Student> found = dao.findById(999L);
        assertFalse(found.isPresent());
    }

    @Test(expected = DaoException.class)
    public void testFindByIdWithInvalidId() throws DaoException {
        dao.findById(-1L);
    }

    @Test
    public void testFindAllStudents() throws DaoException {
        dao.create(new Student("John", "Doe", "john@example.com", "5550101"));
        dao.create(new Student("Jane", "Smith", "jane@example.com", "5550102"));
        dao.create(new Student("Mike", "Johnson", "mike@example.com", "5550103"));

        List<Student> students = dao.findAll();

        assertEquals(3, students.size());
    }

    @Test
    public void testFindAllWithEmptyDatabase() throws DaoException {
        List<Student> students = dao.findAll();

        assertTrue(students.isEmpty());
    }

    @Test
    public void testFindByFirstName() throws DaoException {
        dao.create(new Student("John", "Doe", "john1@example.com", "5550101"));
        dao.create(new Student("John", "Smith", "john2@example.com", "5550102"));
        dao.create(new Student("Jane", "Johnson", "jane@example.com", "5550103"));

        List<Student> johns = dao.findByFirstName("John");

        assertEquals(2, johns.size());
        assertTrue(johns.stream().allMatch(s -> "John".equals(s.getFirstName())));
    }

    @Test
    public void testFindByLastName() throws DaoException {
        dao.create(new Student("John", "Smith", "john@example.com", "5550101"));
        dao.create(new Student("Jane", "Smith", "jane@example.com", "5550102"));

        List<Student> smiths = dao.findByLastName("Smith");

        assertEquals(2, smiths.size());
    }

    @Test
    public void testFindByEmail() throws DaoException {
        Student created = dao.create(new Student("John", "Doe", "john@example.com", "5550101"));

        Optional<Student> found = dao.findByEmail("john@example.com");

        assertTrue(found.isPresent());
        assertEquals(created.getStudentId(), found.get().getStudentId());
    }

    @Test
    public void testFindByEmailNotFound() throws DaoException {
        Optional<Student> found = dao.findByEmail("nonexistent@example.com");
        assertFalse(found.isPresent());
    }

    // ==================== UPDATE TESTS ====================

    @Test
    public void testUpdateStudentWithValidData() throws DaoException {
        Student student = dao.create(new Student("John", "Doe", "john@example.com", "5550101"));

        student.setFirstName("Jonathan");
        student.setPhoneNumber("5550999");
        boolean updated = dao.update(student);

        assertTrue(updated);
        Optional<Student> found = dao.findById(student.getStudentId());
        assertEquals("Jonathan", found.get().getFirstName());
        assertEquals("5550999", found.get().getPhoneNumber());
    }

    @Test
    public void testUpdateNonExistentStudent() throws DaoException {
        Student student = new Student();
        student.setStudentId(999L);
        student.setFirstName("John");
        student.setLastName("Doe");
        student.setEmail("test@example.com");

        boolean updated = dao.update(student);

        assertFalse(updated);
    }

    @Test(expected = DaoException.class)
    public void testUpdateEmailToExistingEmail() throws DaoException {
        Student student1 = dao.create(new Student("John", "Doe", "john@example.com", "5550101"));
        dao.create(new Student("Jane", "Smith", "jane@example.com", "5550102"));

        student1.setEmail("jane@example.com");
        dao.update(student1);
    }

    // ==================== DELETE TESTS ====================

    @Test
    public void testDeleteExistingStudent() throws DaoException {
        Student student = dao.create(new Student("John", "Doe", "john@example.com", "5550101"));

        boolean deleted = dao.delete(student.getStudentId());

        assertTrue(deleted);
        Optional<Student> found = dao.findById(student.getStudentId());
        assertFalse(found.isPresent());
    }

    @Test
    public void testDeleteNonExistentStudent() throws DaoException {
        boolean deleted = dao.delete(999L);
        assertFalse(deleted);
    }

    // ==================== COUNT TESTS ====================

    @Test
    public void testCountStudents() throws DaoException {
        dao.create(new Student("John", "Doe", "john@example.com", "5550101"));
        dao.create(new Student("Jane", "Smith", "jane@example.com", "5550102"));
        dao.create(new Student("Mike", "Johnson", "mike@example.com", "5550103"));

        long count = dao.count();

        assertEquals(3, count);
    }

    @Test
    public void testCountWithEmptyDatabase() throws DaoException {
        long count = dao.count();

        assertEquals(0, count);
    }

    // ==================== INTEGRATION TESTS ====================

    @Test
    public void testCompleteStudentLifecycle() throws DaoException {
        // CREATE
        Student student = dao.create(new Student(
                "John", "Doe", "john@example.com", "5550101",
                LocalDate.of(2005, 1, 15),
                "123 Main St", "Springfield", "IL", "62701",
                LocalDate.now(), "ACTIVE"));

        long studentId = student.getStudentId();

        // READ
        Optional<Student> found = dao.findById(studentId);
        assertTrue(found.isPresent());

        // UPDATE
        found.get().setFirstName("Jonathan");
        found.get().setEnrollmentStatus("INACTIVE");
        boolean updated = dao.update(found.get());
        assertTrue(updated);

        // VERIFY UPDATE
        Optional<Student> updated_student = dao.findById(studentId);
        assertEquals("Jonathan", updated_student.get().getFirstName());
        assertEquals("INACTIVE", updated_student.get().getEnrollmentStatus());

        // DELETE
        boolean deleted = dao.delete(studentId);
        assertTrue(deleted);

        // VERIFY DELETE
        Optional<Student> deleted_student = dao.findById(studentId);
        assertFalse(deleted_student.isPresent());
    }
}
