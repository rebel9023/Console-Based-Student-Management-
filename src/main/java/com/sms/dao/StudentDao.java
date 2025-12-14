package com.sms.dao;

import com.sms.model.Student;
import java.util.List;
import java.util.Optional;

/**
 * Data Access Object interface for Student entity.
 * Defines contract for CRUD operations.
 * 
 * @author SMS Development Team
 * @version 1.0
 * @since 2024
 */
public interface StudentDao {
    
    /**
     * Creates a new student record.
     * 
     * @param student the student to create
     * @return the created student with generated ID
     * @throws DaoException if creation fails
     */
    Student create(Student student) throws DaoException;
    
    /**
     * Retrieves a student by ID.
     * 
     * @param id the student ID
     * @return Optional containing the student if found
     * @throws DaoException if retrieval fails
     */
    Optional<Student> findById(Long id) throws DaoException;
    
    /**
     * Retrieves all students.
     * 
     * @return list of all students
     * @throws DaoException if retrieval fails
     */
    List<Student> findAll() throws DaoException;
    
    /**
     * Retrieves students by first name.
     * 
     * @param firstName the first name to search
     * @return list of matching students
     * @throws DaoException if search fails
     */
    List<Student> findByFirstName(String firstName) throws DaoException;
    
    /**
     * Retrieves students by last name.
     * 
     * @param lastName the last name to search
     * @return list of matching students
     * @throws DaoException if search fails
     */
    List<Student> findByLastName(String lastName) throws DaoException;
    
    /**
     * Retrieves a student by email.
     * 
     * @param email the email to search
     * @return Optional containing the student if found
     * @throws DaoException if search fails
     */
    Optional<Student> findByEmail(String email) throws DaoException;
    
    /**
     * Updates an existing student record.
     * 
     * @param student the student to update
     * @return true if update was successful
     * @throws DaoException if update fails
     */
    boolean update(Student student) throws DaoException;
    
    /**
     * Deletes a student by ID.
     * 
     * @param id the student ID to delete
     * @return true if deletion was successful
     * @throws DaoException if deletion fails
     */
    boolean delete(Long id) throws DaoException;
    
    /**
     * Gets the total count of students.
     * 
     * @return the number of students
     * @throws DaoException if count fails
     */
    long count() throws DaoException;
}
