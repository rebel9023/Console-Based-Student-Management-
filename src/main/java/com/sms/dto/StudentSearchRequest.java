package com.sms.dto;

import com.sms.model.StudentStatus;

/**
 * StudentSearchRequest - DTO for advanced search parameters.
 */
public class StudentSearchRequest {
    
    private String name;
    private String email;
    private String phone;
    private StudentStatus status;
    private Double minGpa;
    private Double maxGpa;
    private Integer pageNumber = 0;
    private Integer pageSize = 10;
    private String sortBy = "id";
    private String sortDirection = "ASC";
    
    // Constructors
    public StudentSearchRequest() {
    }
    
    public StudentSearchRequest(String name, StudentStatus status, Double minGpa, Double maxGpa) {
        this.name = name;
        this.status = status;
        this.minGpa = minGpa;
        this.maxGpa = maxGpa;
    }
    
    // Getters and Setters
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPhone() {
        return phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public StudentStatus getStatus() {
        return status;
    }
    
    public void setStatus(StudentStatus status) {
        this.status = status;
    }
    
    public Double getMinGpa() {
        return minGpa;
    }
    
    public void setMinGpa(Double minGpa) {
        this.minGpa = minGpa;
    }
    
    public Double getMaxGpa() {
        return maxGpa;
    }
    
    public void setMaxGpa(Double maxGpa) {
        this.maxGpa = maxGpa;
    }
    
    public Integer getPageNumber() {
        return pageNumber;
    }
    
    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }
    
    public Integer getPageSize() {
        return pageSize;
    }
    
    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
    
    public String getSortBy() {
        return sortBy;
    }
    
    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }
    
    public String getSortDirection() {
        return sortDirection;
    }
    
    public void setSortDirection(String sortDirection) {
        this.sortDirection = sortDirection;
    }
}
