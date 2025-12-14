package com.sms.repository;

import com.sms.model.StudentEntity;
import com.sms.model.StudentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * StudentRepository - Spring Data JPA repository for Student entity.
 * 
 * Features:
 * - CRUD operations
 * - Custom queries with pagination
 * - Search by name, email, status
 * - Advanced filtering
 * 
 * @author SMS Development Team
 * @version 2.0
 * @since 2024
 */
@Repository
public interface StudentRepository extends JpaRepository<StudentEntity, Integer> {
    
    /**
     * Find student by email (unique constraint)
     */
    Optional<StudentEntity> findByEmail(String email);
    
    /**
     * Find student by phone
     */
    Optional<StudentEntity> findByPhone(String phone);
    
    /**
     * Find students by status
     */
    List<StudentEntity> findByStatus(StudentStatus status);
    
    /**
     * Find students by status with pagination
     */
    Page<StudentEntity> findByStatus(StudentStatus status, Pageable pageable);
    
    /**
     * Search students by name (case-insensitive)
     */
    @Query("SELECT s FROM StudentEntity s WHERE LOWER(s.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<StudentEntity> searchByName(@Param("name") String name);
    
    /**
     * Search students by name with pagination
     */
    @Query("SELECT s FROM StudentEntity s WHERE LOWER(s.name) LIKE LOWER(CONCAT('%', :name, '%')) ORDER BY s.name ASC")
    Page<StudentEntity> searchByNamePaginated(@Param("name") String name, Pageable pageable);
    
    /**
     * Search students by name or email
     */
    @Query("SELECT s FROM StudentEntity s WHERE LOWER(s.name) LIKE LOWER(CONCAT('%', :search, '%')) " +
           "OR LOWER(s.email) LIKE LOWER(CONCAT('%', :search, '%'))")
    List<StudentEntity> searchByNameOrEmail(@Param("search") String search);
    
    /**
     * Filter students by GPA range
     */
    @Query("SELECT s FROM StudentEntity s WHERE s.gpa >= :minGpa AND s.gpa <= :maxGpa")
    List<StudentEntity> findByGpaRange(@Param("minGpa") Double minGpa, @Param("maxGpa") Double maxGpa);
    
    /**
     * Filter students by GPA range with pagination
     */
    @Query("SELECT s FROM StudentEntity s WHERE s.gpa >= :minGpa AND s.gpa <= :maxGpa ORDER BY s.gpa DESC")
    Page<StudentEntity> findByGpaRangePaginated(@Param("minGpa") Double minGpa, @Param("maxGpa") Double maxGpa, Pageable pageable);
    
    /**
     * Get count of active students
     */
    Long countByStatus(StudentStatus status);
    
    /**
     * Check if email exists (excluding given ID)
     */
    @Query("SELECT CASE WHEN COUNT(s) > 0 THEN true ELSE false END FROM StudentEntity s WHERE s.email = :email AND s.id != :id")
    boolean existsByEmailAndIdNot(@Param("email") String email, @Param("id") Integer id);
}
