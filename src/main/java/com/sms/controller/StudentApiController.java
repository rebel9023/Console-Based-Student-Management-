package com.sms.controller;

import com.sms.dto.StudentDTO;
import com.sms.dto.StudentSearchRequest;
import com.sms.exception.ResourceNotFoundException;
import com.sms.model.StudentEntity;
import com.sms.model.StudentStatus;
import com.sms.service.StudentApiService;
import com.sms.util.ApiResponseWrapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * REST API Controller for Student Management.
 * 
 * Base URL: /api/v1/students
 * 
 * Features:
 * - CRUD operations via REST endpoints
 * - Advanced search and filtering
 * - Pagination support
 * - Swagger/OpenAPI documentation
 * 
 * @author SMS Development Team
 * @version 2.0
 * @since 2024
 */
@RestController
@RequestMapping("/api/v1/students")
@Tag(name = "Student Management", description = "REST API for managing students")
@CrossOrigin(origins = "*", maxAge = 3600)
public class StudentApiController {
    
    @Autowired
    private StudentApiService studentApiService;
    
    /**
     * Get all students with pagination
     */
    @GetMapping
    @Operation(summary = "Get all students", description = "Retrieve all students with pagination support")
    @ApiResponse(responseCode = "200", description = "Students retrieved successfully")
    public ResponseEntity<?> getAllStudents(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<StudentEntity> students = studentApiService.getAllStudents(PageRequest.of(page, size));
        Page<StudentDTO> dtoPage = students.map(this::convertToDTO);
        return ResponseEntity.ok(new ApiResponseWrapper(true, "Students retrieved successfully", dtoPage));
    }
    
    /**
     * Get student by ID
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get student by ID", description = "Retrieve a specific student by their ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Student found"),
        @ApiResponse(responseCode = "404", description = "Student not found")
    })
    public ResponseEntity<?> getStudentById(@PathVariable Integer id) {
        StudentEntity student = studentApiService.getStudentById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with ID: " + id));
        return ResponseEntity.ok(new ApiResponseWrapper(true, "Student retrieved successfully", convertToDTO(student)));
    }
    
    /**
     * Create new student
     */
    @PostMapping
    @Operation(summary = "Create new student", description = "Add a new student to the system")
    @ApiResponse(responseCode = "201", description = "Student created successfully")
    public ResponseEntity<?> createStudent(@Valid @RequestBody StudentDTO studentDTO) {
        StudentEntity student = studentApiService.createStudent(convertToEntity(studentDTO));
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponseWrapper(true, "Student created successfully", convertToDTO(student)));
    }
    
    /**
     * Update existing student
     */
    @PutMapping("/{id}")
    @Operation(summary = "Update student", description = "Update an existing student's information")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Student updated successfully"),
        @ApiResponse(responseCode = "404", description = "Student not found")
    })
    public ResponseEntity<?> updateStudent(
            @PathVariable Integer id,
            @Valid @RequestBody StudentDTO studentDTO) {
        StudentEntity student = studentApiService.updateStudent(id, convertToEntity(studentDTO))
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with ID: " + id));
        return ResponseEntity.ok(new ApiResponseWrapper(true, "Student updated successfully", convertToDTO(student)));
    }
    
    /**
     * Delete student
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete student", description = "Remove a student from the system")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Student deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Student not found")
    })
    public ResponseEntity<?> deleteStudent(@PathVariable Integer id) {
        boolean deleted = studentApiService.deleteStudent(id);
        if (!deleted) {
            throw new ResourceNotFoundException("Student not found with ID: " + id);
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(new ApiResponseWrapper(true, "Student deleted successfully", null));
    }
    
    /**
     * Search students by name
     */
    @GetMapping("/search/name")
    @Operation(summary = "Search by name", description = "Search students by name (case-insensitive)")
    public ResponseEntity<?> searchByName(
            @RequestParam String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<StudentEntity> students = studentApiService.searchByName(name, PageRequest.of(page, size));
        Page<StudentDTO> dtoPage = students.map(this::convertToDTO);
        return ResponseEntity.ok(new ApiResponseWrapper(true, "Search results", dtoPage));
    }
    
    /**
     * Search students by status
     */
    @GetMapping("/search/status")
    @Operation(summary = "Search by status", description = "Get all students with specific status")
    public ResponseEntity<?> searchByStatus(
            @RequestParam StudentStatus status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<StudentEntity> students = studentApiService.getStudentsByStatus(status, PageRequest.of(page, size));
        Page<StudentDTO> dtoPage = students.map(this::convertToDTO);
        return ResponseEntity.ok(new ApiResponseWrapper(true, "Students with status: " + status, dtoPage));
    }
    
    /**
     * Filter by GPA range
     */
    @GetMapping("/search/gpa")
    @Operation(summary = "Filter by GPA range", description = "Get students within a GPA range")
    public ResponseEntity<?> filterByGpaRange(
            @RequestParam Double minGpa,
            @RequestParam Double maxGpa,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<StudentEntity> students = studentApiService.filterByGpaRange(minGpa, maxGpa, PageRequest.of(page, size));
        Page<StudentDTO> dtoPage = students.map(this::convertToDTO);
        return ResponseEntity.ok(new ApiResponseWrapper(true, "GPA Range: " + minGpa + " - " + maxGpa, dtoPage));
    }
    
    /**
     * Get statistics
     */
    @GetMapping("/statistics")
    @Operation(summary = "Get statistics", description = "Get system statistics about students")
    public ResponseEntity<?> getStatistics() {
        StudentStatistics stats = studentApiService.getStatistics();
        return ResponseEntity.ok(new ApiResponseWrapper(true, "Statistics retrieved", stats));
    }
    
    /**
     * Convert StudentEntity to StudentDTO
     */
    private StudentDTO convertToDTO(StudentEntity entity) {
        StudentDTO dto = new StudentDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setEmail(entity.getEmail());
        dto.setPhone(entity.getPhone());
        dto.setGpa(entity.getGpa());
        dto.setStatus(entity.getStatus());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        return dto;
    }
    
    /**
     * Convert StudentDTO to StudentEntity
     */
    private StudentEntity convertToEntity(StudentDTO dto) {
        StudentEntity entity = new StudentEntity();
        if (dto.getId() != null) {
            entity.setId(dto.getId());
        }
        entity.setName(dto.getName());
        entity.setEmail(dto.getEmail());
        entity.setPhone(dto.getPhone());
        entity.setGpa(dto.getGpa());
        if (dto.getStatus() != null) {
            entity.setStatus(dto.getStatus());
        }
        return entity;
    }
}
