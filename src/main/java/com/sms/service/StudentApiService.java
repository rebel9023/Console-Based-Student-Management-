package com.sms.service;

import com.sms.controller.StudentStatistics;
import com.sms.model.StudentEntity;
import com.sms.model.StudentStatus;
import com.sms.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * StudentApiService - Service layer for REST API operations.
 * Handles business logic for student management via Spring Data JPA.
 */
@Service
@Transactional
public class StudentApiService {
    
    @Autowired
    private StudentRepository studentRepository;
    
    /**
     * Get all students with pagination
     */
    public Page<StudentEntity> getAllStudents(Pageable pageable) {
        return studentRepository.findAll(pageable);
    }
    
    /**
     * Get student by ID
     */
    public Optional<StudentEntity> getStudentById(Integer id) {
        return studentRepository.findById(id);
    }
    
    /**
     * Create new student
     */
    public StudentEntity createStudent(StudentEntity student) {
        if (student.getStatus() == null) {
            student.setStatus(StudentStatus.ACTIVE);
        }
        return studentRepository.save(student);
    }
    
    /**
     * Update existing student
     */
    public Optional<StudentEntity> updateStudent(Integer id, StudentEntity updatedStudent) {
        return studentRepository.findById(id).map(existing -> {
            existing.setName(updatedStudent.getName());
            existing.setEmail(updatedStudent.getEmail());
            existing.setPhone(updatedStudent.getPhone());
            existing.setGpa(updatedStudent.getGpa());
            if (updatedStudent.getStatus() != null) {
                existing.setStatus(updatedStudent.getStatus());
            }
            return studentRepository.save(existing);
        });
    }
    
    /**
     * Delete student by ID
     */
    public boolean deleteStudent(Integer id) {
        if (studentRepository.existsById(id)) {
            studentRepository.deleteById(id);
            return true;
        }
        return false;
    }
    
    /**
     * Search students by name
     */
    public Page<StudentEntity> searchByName(String name, Pageable pageable) {
        return studentRepository.searchByNamePaginated(name, pageable);
    }
    
    /**
     * Get students by status
     */
    public Page<StudentEntity> getStudentsByStatus(StudentStatus status, Pageable pageable) {
        return studentRepository.findByStatus(status, pageable);
    }
    
    /**
     * Filter students by GPA range
     */
    public Page<StudentEntity> filterByGpaRange(Double minGpa, Double maxGpa, Pageable pageable) {
        return studentRepository.findByGpaRangePaginated(minGpa, maxGpa, pageable);
    }
    
    /**
     * Get system statistics
     */
    @Transactional(readOnly = true)
    public StudentStatistics getStatistics() {
        StudentStatistics stats = new StudentStatistics();
        
        // Total count
        stats.setTotalStudents(studentRepository.count());
        
        // Count by status
        stats.setActiveStudents(studentRepository.countByStatus(StudentStatus.ACTIVE));
        stats.setInactiveStudents(studentRepository.countByStatus(StudentStatus.INACTIVE));
        stats.setSuspendedStudents(studentRepository.countByStatus(StudentStatus.SUSPENDED));
        stats.setGraduatedStudents(studentRepository.countByStatus(StudentStatus.GRADUATED));
        
        // GPA statistics would require additional query methods
        // For now, return zeros
        stats.setAverageGpa(0.0);
        stats.setHighestGpa(0.0);
        stats.setLowestGpa(0.0);
        
        return stats;
    }
}
