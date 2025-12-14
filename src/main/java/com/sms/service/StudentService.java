package com.sms.service;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import com.sms.dao.DaoException;
import com.sms.dao.StudentDao;
import com.sms.model.Student;

/**
 * Service layer for Student business logic.
 * Provides validation and orchestration of DAO operations.
 * 
 * @author SMS Development Team
 * @version 1.0
 * @since 2024
 */
public class StudentService {
    
    private StudentDao studentDao;
    
    // Validation patterns
    private static final Pattern EMAIL_PATTERN = 
        Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
    
    private static final Pattern PHONE_PATTERN = 
        Pattern.compile("^[\\d\\-\\+\\s\\(\\)]{10,}$");
    
    private static final Pattern NAME_PATTERN = 
        Pattern.compile("^[a-zA-Z\\s'-]{2,50}$");
    
    /**
     * Constructor with StudentDao dependency injection.
     * 
     * @param studentDao the DAO implementation to use
     */
    public StudentService(StudentDao studentDao) {
        if (studentDao == null) {
            throw new IllegalArgumentException("StudentDao cannot be null");
        }
        this.studentDao = studentDao;
    }
    
    /**
     * Creates a new student with validation.
     * 
     * @param firstName the first name
     * @param lastName the last name
     * @param email the email address
     * @param phoneNumber the phone number
     * @return the created student
     * @throws ServiceException if validation fails or creation fails
     */
    public Student addStudent(String firstName, String lastName, String email, String phoneNumber) 
            throws ServiceException {
        
        // Validate inputs
        validateName(firstName, "First name");
        validateName(lastName, "Last name");
        validateEmail(email);
        validatePhone(phoneNumber);
        
        Student student = new Student(firstName, lastName, email, phoneNumber);
        
        try {
            return studentDao.create(student);
        } catch (DaoException e) {
            throw new ServiceException("Failed to add student: " + e.getMessage(), e);
        }
    }
    
    /**
     * Retrieves a student by ID.
     * 
     * @param id the student ID
     * @return Optional containing the student if found
     * @throws ServiceException if operation fails
     */
    public Optional<Student> getStudent(Long id) throws ServiceException {
        if (id == null || id <= 0) {
            throw new ServiceException("Invalid student ID");
        }
        
        try {
            return studentDao.findById(id);
        } catch (DaoException e) {
            throw new ServiceException("Failed to retrieve student: " + e.getMessage(), e);
        }
    }
    
    /**
     * Retrieves all students.
     * 
     * @return list of all students
     * @throws ServiceException if operation fails
     */
    public List<Student> getAllStudents() throws ServiceException {
        try {
            return studentDao.findAll();
        } catch (DaoException e) {
            throw new ServiceException("Failed to retrieve students: " + e.getMessage(), e);
        }
    }
    
    /**
     * Searches for students by first name.
     * 
     * @param firstName the first name to search
     * @return list of matching students
     * @throws ServiceException if operation fails
     */
    public List<Student> searchByFirstName(String firstName) throws ServiceException {
        if (firstName == null || firstName.trim().isEmpty()) {
            throw new ServiceException("First name cannot be empty");
        }
        
        try {
            return studentDao.findByFirstName(firstName);
        } catch (DaoException e) {
            throw new ServiceException("Failed to search: " + e.getMessage(), e);
        }
    }
    
    /**
     * Searches for students by last name.
     * 
     * @param lastName the last name to search
     * @return list of matching students
     * @throws ServiceException if operation fails
     */
    public List<Student> searchByLastName(String lastName) throws ServiceException {
        if (lastName == null || lastName.trim().isEmpty()) {
            throw new ServiceException("Last name cannot be empty");
        }
        
        try {
            return studentDao.findByLastName(lastName);
        } catch (DaoException e) {
            throw new ServiceException("Failed to search: " + e.getMessage(), e);
        }
    }
    
    /**
     * Searches for a student by email.
     * 
     * @param email the email to search
     * @return Optional containing the student if found
     * @throws ServiceException if operation fails
     */
    public Optional<Student> searchByEmail(String email) throws ServiceException {
        if (email == null || email.trim().isEmpty()) {
            throw new ServiceException("Email cannot be empty");
        }
        
        try {
            return studentDao.findByEmail(email);
        } catch (DaoException e) {
            throw new ServiceException("Failed to search: " + e.getMessage(), e);
        }
    }
    
    /**
     * Updates an existing student.
     * 
     * @param student the student with updated data
     * @return true if update was successful
     * @throws ServiceException if validation fails or update fails
     */
    public boolean updateStudent(Student student) throws ServiceException {
        if (student == null || student.getStudentId() == null) {
            throw new ServiceException("Invalid student data");
        }
        
        validateName(student.getFirstName(), "First name");
        validateName(student.getLastName(), "Last name");
        validateEmail(student.getEmail());
        
        if (student.getPhoneNumber() != null && !student.getPhoneNumber().isEmpty()) {
            validatePhone(student.getPhoneNumber());
        }
        
        try {
            boolean updated = studentDao.update(student);
            return updated;
        } catch (DaoException e) {
            throw new ServiceException("Failed to update student: " + e.getMessage(), e);
        }
    }
    
    /**
     * Deletes a student by ID.
     * 
     * @param id the student ID to delete
     * @return true if deletion was successful
     * @throws ServiceException if operation fails
     */
    public boolean deleteStudent(Long id) throws ServiceException {
        if (id == null || id <= 0) {
            throw new ServiceException("Invalid student ID");
        }
        
        try {
            boolean deleted = studentDao.delete(id);
            return deleted;
        } catch (DaoException e) {
            throw new ServiceException("Failed to delete student: " + e.getMessage(), e);
        }
    }
    
    /**
     * Gets the total count of students.
     * 
     * @return the number of students
     * @throws ServiceException if operation fails
     */
    public long getStudentCount() throws ServiceException {
        try {
            return studentDao.count();
        } catch (DaoException e) {
            throw new ServiceException("Failed to count students: " + e.getMessage(), e);
        }
    }
    
    /**
     * Validates a name field.
     * 
     * @param name the name to validate
     * @param fieldName the name of the field (for error messages)
     * @throws ServiceException if validation fails
     */
    private void validateName(String name, String fieldName) throws ServiceException {
        if (name == null || name.trim().isEmpty()) {
            throw new ServiceException(fieldName + " cannot be empty");
        }
        
        if (!NAME_PATTERN.matcher(name.trim()).matches()) {
            throw new ServiceException(fieldName + " must be 2-50 characters and contain only letters, spaces, hyphens, or apostrophes");
        }
    }
    
    /**
     * Validates an email address.
     * 
     * @param email the email to validate
     * @throws ServiceException if validation fails
     */
    private void validateEmail(String email) throws ServiceException {
        if (email == null || email.trim().isEmpty()) {
            throw new ServiceException("Email cannot be empty");
        }
        
        if (!EMAIL_PATTERN.matcher(email.trim()).matches()) {
            throw new ServiceException("Invalid email format");
        }
        
        if (email.length() > 100) {
            throw new ServiceException("Email is too long (max 100 characters)");
        }
    }
    
    /**
     * Validates a phone number.
     * 
     * @param phone the phone number to validate
     * @throws ServiceException if validation fails
     */
    private void validatePhone(String phone) throws ServiceException {
        if (phone == null || phone.trim().isEmpty()) {
            throw new ServiceException("Phone number cannot be empty");
        }
        
        String cleanPhone = phone.replaceAll("[\\D]", "");
        if (cleanPhone.length() < 10) {
            throw new ServiceException("Phone number must contain at least 10 digits");
        }
    }
}
