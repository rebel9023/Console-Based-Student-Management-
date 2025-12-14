package com.sms.dao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.sms.model.Student;

/**
 * In-memory implementation of StudentDao using ArrayList.
 * This implementation stores all data in memory (Phase 1).
 * 
 * @author SMS Development Team
 * @version 1.0
 * @since 2024
 */
public class StudentDaoMemoryImpl implements StudentDao {
    
    private List<Student> students;
    private Long nextId = 1L;
    
    /**
     * Constructor initializes the ArrayList.
     */
    public StudentDaoMemoryImpl() {
        this.students = new ArrayList<>();
    }
    
    @Override
    public Student create(Student student) throws DaoException {
        if (student == null) {
            throw new DaoException("Student cannot be null");
        }
        
        // Check for duplicate email
        if (students.stream().anyMatch(s -> s.getEmail().equals(student.getEmail()))) {
            throw new DaoException("Student with email '" + student.getEmail() + "' already exists");
        }
        
        // Set auto-generated ID
        student.setStudentId(nextId++);
        
        // Set default enrollment date if not set
        if (student.getEnrollmentDate() == null) {
            student.setEnrollmentDate(LocalDate.now());
        }
        
        // Set default status if not set
        if (student.getEnrollmentStatus() == null) {
            student.setEnrollmentStatus("ACTIVE");
        }
        
        students.add(student);
        return student;
    }
    
    @Override
    public Optional<Student> findById(Long id) throws DaoException {
        if (id == null || id <= 0) {
            throw new DaoException("Invalid student ID");
        }
        return students.stream()
                .filter(s -> s.getStudentId().equals(id))
                .findFirst();
    }
    
    @Override
    public List<Student> findAll() throws DaoException {
        return new ArrayList<>(students);
    }
    
    @Override
    public List<Student> findByFirstName(String firstName) throws DaoException {
        if (firstName == null || firstName.trim().isEmpty()) {
            throw new DaoException("First name cannot be empty");
        }
        return students.stream()
                .filter(s -> s.getFirstName().equalsIgnoreCase(firstName.trim()))
                .collect(Collectors.toList());
    }
    
    @Override
    public List<Student> findByLastName(String lastName) throws DaoException {
        if (lastName == null || lastName.trim().isEmpty()) {
            throw new DaoException("Last name cannot be empty");
        }
        return students.stream()
                .filter(s -> s.getLastName().equalsIgnoreCase(lastName.trim()))
                .collect(Collectors.toList());
    }
    
    @Override
    public Optional<Student> findByEmail(String email) throws DaoException {
        if (email == null || email.trim().isEmpty()) {
            throw new DaoException("Email cannot be empty");
        }
        return students.stream()
                .filter(s -> s.getEmail().equalsIgnoreCase(email.trim()))
                .findFirst();
    }
    
    @Override
    public boolean update(Student student) throws DaoException {
        if (student == null || student.getStudentId() == null) {
            throw new DaoException("Invalid student data for update");
        }
        
        Optional<Student> existingStudent = findById(student.getStudentId());
        
        if (existingStudent.isPresent()) {
            Student existing = existingStudent.get();
            
            // Check for email conflict (if email is being changed)
            if (!existing.getEmail().equals(student.getEmail())) {
                if (students.stream()
                        .anyMatch(s -> s.getEmail().equals(student.getEmail()) &&
                                !s.getStudentId().equals(student.getStudentId()))) {
                    throw new DaoException("Email '" + student.getEmail() + "' already in use");
                }
            }
            
            // Update all fields
            existing.setFirstName(student.getFirstName());
            existing.setLastName(student.getLastName());
            existing.setEmail(student.getEmail());
            existing.setPhoneNumber(student.getPhoneNumber());
            existing.setDateOfBirth(student.getDateOfBirth());
            existing.setAddress(student.getAddress());
            existing.setCity(student.getCity());
            existing.setState(student.getState());
            existing.setZipCode(student.getZipCode());
            existing.setEnrollmentDate(student.getEnrollmentDate());
            existing.setEnrollmentStatus(student.getEnrollmentStatus());
            
            return true;
        }
        
        return false;
    }
    
    @Override
    public boolean delete(Long id) throws DaoException {
        if (id == null || id <= 0) {
            throw new DaoException("Invalid student ID");
        }
        return students.removeIf(s -> s.getStudentId().equals(id));
    }
    
    @Override
    public long count() throws DaoException {
        return students.size();
    }
    
    /**
     * Clears all students from memory. Useful for testing.
     */
    public void clear() {
        students.clear();
        nextId = 1L;
    }
}
