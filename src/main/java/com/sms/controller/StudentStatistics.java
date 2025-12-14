package com.sms.controller;

/**
 * StudentStatistics - DTO for system statistics.
 */
public class StudentStatistics {
    
    private Long totalStudents;
    private Long activeStudents;
    private Long inactiveStudents;
    private Long suspendedStudents;
    private Long graduatedStudents;
    private Double averageGpa;
    private Double highestGpa;
    private Double lowestGpa;
    
    public StudentStatistics() {
    }
    
    public StudentStatistics(Long totalStudents, Long activeStudents, Long inactiveStudents,
                             Long suspendedStudents, Long graduatedStudents, Double averageGpa,
                             Double highestGpa, Double lowestGpa) {
        this.totalStudents = totalStudents;
        this.activeStudents = activeStudents;
        this.inactiveStudents = inactiveStudents;
        this.suspendedStudents = suspendedStudents;
        this.graduatedStudents = graduatedStudents;
        this.averageGpa = averageGpa;
        this.highestGpa = highestGpa;
        this.lowestGpa = lowestGpa;
    }
    
    // Getters and Setters
    public Long getTotalStudents() {
        return totalStudents;
    }
    
    public void setTotalStudents(Long totalStudents) {
        this.totalStudents = totalStudents;
    }
    
    public Long getActiveStudents() {
        return activeStudents;
    }
    
    public void setActiveStudents(Long activeStudents) {
        this.activeStudents = activeStudents;
    }
    
    public Long getInactiveStudents() {
        return inactiveStudents;
    }
    
    public void setInactiveStudents(Long inactiveStudents) {
        this.inactiveStudents = inactiveStudents;
    }
    
    public Long getSuspendedStudents() {
        return suspendedStudents;
    }
    
    public void setSuspendedStudents(Long suspendedStudents) {
        this.suspendedStudents = suspendedStudents;
    }
    
    public Long getGraduatedStudents() {
        return graduatedStudents;
    }
    
    public void setGraduatedStudents(Long graduatedStudents) {
        this.graduatedStudents = graduatedStudents;
    }
    
    public Double getAverageGpa() {
        return averageGpa;
    }
    
    public void setAverageGpa(Double averageGpa) {
        this.averageGpa = averageGpa;
    }
    
    public Double getHighestGpa() {
        return highestGpa;
    }
    
    public void setHighestGpa(Double highestGpa) {
        this.highestGpa = highestGpa;
    }
    
    public Double getLowestGpa() {
        return lowestGpa;
    }
    
    public void setLowestGpa(Double lowestGpa) {
        this.lowestGpa = lowestGpa;
    }
}
