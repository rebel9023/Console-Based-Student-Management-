package com.sms.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sms.model.Student;
import com.sms.util.DatabaseUtil;

/**
 * JDBC implementation of StudentDao using MySQL database.
 * Uses PreparedStatement to prevent SQL injection.
 * This implementation is for Phase 2 with database persistence.
 * 
 * @author SMS Development Team
 * @version 1.0
 * @since 2024
 */
public class StudentDaoJdbcImpl implements StudentDao {
    
    private static final Logger logger = LoggerFactory.getLogger(StudentDaoJdbcImpl.class);
    
    // SQL Queries
    private static final String CREATE_STUDENT = 
        "INSERT INTO students (first_name, last_name, email, phone_number, date_of_birth, " +
        "address, city, state, zip_code, enrollment_date, enrollment_status) " +
        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    
    private static final String FIND_BY_ID = 
        "SELECT * FROM students WHERE student_id = ?";
    
    private static final String FIND_ALL = 
        "SELECT * FROM students ORDER BY first_name, last_name";
    
    private static final String FIND_BY_FIRST_NAME = 
        "SELECT * FROM students WHERE first_name = ? ORDER BY last_name";
    
    private static final String FIND_BY_LAST_NAME = 
        "SELECT * FROM students WHERE last_name = ? ORDER BY first_name";
    
    private static final String FIND_BY_EMAIL = 
        "SELECT * FROM students WHERE email = ?";
    
    private static final String UPDATE_STUDENT = 
        "UPDATE students SET first_name = ?, last_name = ?, email = ?, phone_number = ?, " +
        "date_of_birth = ?, address = ?, city = ?, state = ?, zip_code = ?, " +
        "enrollment_date = ?, enrollment_status = ? WHERE student_id = ?";
    
    private static final String DELETE_STUDENT = 
        "DELETE FROM students WHERE student_id = ?";
    
    private static final String COUNT_STUDENTS = 
        "SELECT COUNT(*) FROM students";
    
    @Override
    public Student create(Student student) throws DaoException {
        if (student == null) {
            throw new DaoException("Student cannot be null");
        }
        
        // Check for duplicate email
        try {
            Optional<Student> existing = findByEmail(student.getEmail());
            if (existing.isPresent()) {
                throw new DaoException("Student with email '" + student.getEmail() + "' already exists");
            }
        } catch (DaoException e) {
            logger.error("Error checking for duplicate email", e);
            throw e;
        }
        
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(CREATE_STUDENT, 
                     Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setString(1, student.getFirstName());
            pstmt.setString(2, student.getLastName());
            pstmt.setString(3, student.getEmail());
            pstmt.setString(4, student.getPhoneNumber());
            pstmt.setDate(5, student.getDateOfBirth() != null ? 
                    Date.valueOf(student.getDateOfBirth()) : null);
            pstmt.setString(6, student.getAddress());
            pstmt.setString(7, student.getCity());
            pstmt.setString(8, student.getState());
            pstmt.setString(9, student.getZipCode());
            pstmt.setDate(10, student.getEnrollmentDate() != null ? 
                    Date.valueOf(student.getEnrollmentDate()) : Date.valueOf(LocalDate.now()));
            pstmt.setString(11, student.getEnrollmentStatus() != null ? 
                    student.getEnrollmentStatus() : "ACTIVE");
            
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        student.setStudentId(generatedKeys.getLong(1));
                        logger.info("Student created successfully with ID: {}", student.getStudentId());
                        return student;
                    }
                }
            }
            
            throw new DaoException("Failed to create student - no ID generated");
            
        } catch (SQLException e) {
            logger.error("Error creating student", e);
            throw new DaoException("Failed to create student: " + e.getMessage(), e);
        }
    }
    
    @Override
    public Optional<Student> findById(Long id) throws DaoException {
        if (id == null || id <= 0) {
            throw new DaoException("Invalid student ID");
        }
        
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(FIND_BY_ID)) {
            
            pstmt.setLong(1, id);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToStudent(rs));
                }
            }
            
            return Optional.empty();
            
        } catch (SQLException e) {
            logger.error("Error finding student by ID: {}", id, e);
            throw new DaoException("Failed to find student: " + e.getMessage(), e);
        }
    }
    
    @Override
    public List<Student> findAll() throws DaoException {
        List<Student> students = new ArrayList<>();
        
        try (Connection connection = DatabaseUtil.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(FIND_ALL)) {
            
            while (rs.next()) {
                students.add(mapResultSetToStudent(rs));
            }
            
            logger.debug("Found {} students", students.size());
            return students;
            
        } catch (SQLException e) {
            logger.error("Error finding all students", e);
            throw new DaoException("Failed to retrieve students: " + e.getMessage(), e);
        }
    }
    
    @Override
    public List<Student> findByFirstName(String firstName) throws DaoException {
        if (firstName == null || firstName.trim().isEmpty()) {
            throw new DaoException("First name cannot be empty");
        }
        
        List<Student> students = new ArrayList<>();
        
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(FIND_BY_FIRST_NAME)) {
            
            pstmt.setString(1, firstName.trim());
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    students.add(mapResultSetToStudent(rs));
                }
            }
            
            return students;
            
        } catch (SQLException e) {
            logger.error("Error finding students by first name: {}", firstName, e);
            throw new DaoException("Failed to search by first name: " + e.getMessage(), e);
        }
    }
    
    @Override
    public List<Student> findByLastName(String lastName) throws DaoException {
        if (lastName == null || lastName.trim().isEmpty()) {
            throw new DaoException("Last name cannot be empty");
        }
        
        List<Student> students = new ArrayList<>();
        
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(FIND_BY_LAST_NAME)) {
            
            pstmt.setString(1, lastName.trim());
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    students.add(mapResultSetToStudent(rs));
                }
            }
            
            return students;
            
        } catch (SQLException e) {
            logger.error("Error finding students by last name: {}", lastName, e);
            throw new DaoException("Failed to search by last name: " + e.getMessage(), e);
        }
    }
    
    @Override
    public Optional<Student> findByEmail(String email) throws DaoException {
        if (email == null || email.trim().isEmpty()) {
            throw new DaoException("Email cannot be empty");
        }
        
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(FIND_BY_EMAIL)) {
            
            pstmt.setString(1, email.trim());
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(mapResultSetToStudent(rs));
                }
            }
            
            return Optional.empty();
            
        } catch (SQLException e) {
            logger.error("Error finding student by email: {}", email, e);
            throw new DaoException("Failed to find student by email: " + e.getMessage(), e);
        }
    }
    
    @Override
    public boolean update(Student student) throws DaoException {
        if (student == null || student.getStudentId() == null) {
            throw new DaoException("Invalid student data for update");
        }
        
        // Check for email conflict
        try {
            Optional<Student> existing = findById(student.getStudentId());
            if (!existing.isPresent()) {
                return false;
            }
            
            if (!existing.get().getEmail().equals(student.getEmail())) {
                Optional<Student> emailConflict = findByEmail(student.getEmail());
                if (emailConflict.isPresent()) {
                    throw new DaoException("Email '" + student.getEmail() + "' already in use");
                }
            }
        } catch (DaoException e) {
            logger.error("Error checking for email conflict", e);
            throw e;
        }
        
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(UPDATE_STUDENT)) {
            
            pstmt.setString(1, student.getFirstName());
            pstmt.setString(2, student.getLastName());
            pstmt.setString(3, student.getEmail());
            pstmt.setString(4, student.getPhoneNumber());
            pstmt.setDate(5, student.getDateOfBirth() != null ? 
                    Date.valueOf(student.getDateOfBirth()) : null);
            pstmt.setString(6, student.getAddress());
            pstmt.setString(7, student.getCity());
            pstmt.setString(8, student.getState());
            pstmt.setString(9, student.getZipCode());
            pstmt.setDate(10, student.getEnrollmentDate() != null ? 
                    Date.valueOf(student.getEnrollmentDate()) : Date.valueOf(LocalDate.now()));
            pstmt.setString(11, student.getEnrollmentStatus());
            pstmt.setLong(12, student.getStudentId());
            
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows > 0) {
                logger.info("Student updated successfully with ID: {}", student.getStudentId());
                return true;
            }
            
            return false;
            
        } catch (SQLException e) {
            logger.error("Error updating student", e);
            throw new DaoException("Failed to update student: " + e.getMessage(), e);
        }
    }
    
    @Override
    public boolean delete(Long id) throws DaoException {
        if (id == null || id <= 0) {
            throw new DaoException("Invalid student ID");
        }
        
        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(DELETE_STUDENT)) {
            
            pstmt.setLong(1, id);
            
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows > 0) {
                logger.info("Student deleted successfully with ID: {}", id);
                return true;
            }
            
            return false;
            
        } catch (SQLException e) {
            logger.error("Error deleting student with ID: {}", id, e);
            throw new DaoException("Failed to delete student: " + e.getMessage(), e);
        }
    }
    
    @Override
    public long count() throws DaoException {
        try (Connection connection = DatabaseUtil.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(COUNT_STUDENTS)) {
            
            if (rs.next()) {
                return rs.getLong(1);
            }
            
            return 0;
            
        } catch (SQLException e) {
            logger.error("Error counting students", e);
            throw new DaoException("Failed to count students: " + e.getMessage(), e);
        }
    }
    
    /**
     * Maps a ResultSet row to a Student object.
     * 
     * @param rs the ResultSet to map
     * @return the mapped Student object
     * @throws SQLException if mapping fails
     */
    private Student mapResultSetToStudent(ResultSet rs) throws SQLException {
        Student student = new Student();
        student.setStudentId(rs.getLong("student_id"));
        student.setFirstName(rs.getString("first_name"));
        student.setLastName(rs.getString("last_name"));
        student.setEmail(rs.getString("email"));
        student.setPhoneNumber(rs.getString("phone_number"));
        
        Date dob = rs.getDate("date_of_birth");
        if (dob != null) {
            student.setDateOfBirth(dob.toLocalDate());
        }
        
        student.setAddress(rs.getString("address"));
        student.setCity(rs.getString("city"));
        student.setState(rs.getString("state"));
        student.setZipCode(rs.getString("zip_code"));
        
        Date enrollmentDate = rs.getDate("enrollment_date");
        if (enrollmentDate != null) {
            student.setEnrollmentDate(enrollmentDate.toLocalDate());
        }
        
        student.setEnrollmentStatus(rs.getString("enrollment_status"));
        
        return student;
    }
}
