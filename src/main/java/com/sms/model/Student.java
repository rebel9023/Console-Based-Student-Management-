package com.sms.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * Student entity class representing a student record in the system.
 * This class implements Serializable for potential persistence operations.
 * 
 * @author SMS Development Team
 * @version 1.0
 * @since 2024
 */
public class Student implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private Long studentId;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private LocalDate dateOfBirth;
    private String address;
    private String city;
    private String state;
    private String zipCode;
    private LocalDate enrollmentDate;
    private String enrollmentStatus;
    
    /**
     * Default constructor.
     */
    public Student() {
    }
    
    /**
     * Constructor with essential fields.
     * 
     * @param firstName the first name of the student
     * @param lastName the last name of the student
     * @param email the email address of the student
     * @param phoneNumber the phone number of the student
     */
    public Student(String firstName, String lastName, String email, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.enrollmentStatus = "ACTIVE";
        this.enrollmentDate = LocalDate.now();
    }
    
    /**
     * Constructor with all fields except studentId (auto-generated).
     */
    public Student(String firstName, String lastName, String email, String phoneNumber,
                   LocalDate dateOfBirth, String address, String city, String state,
                   String zipCode, LocalDate enrollmentDate, String enrollmentStatus) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
        this.enrollmentDate = enrollmentDate;
        this.enrollmentStatus = enrollmentStatus;
    }
    
    /**
     * Constructor with all fields including studentId.
     */
    public Student(Long studentId, String firstName, String lastName, String email,
                   String phoneNumber, LocalDate dateOfBirth, String address, String city,
                   String state, String zipCode, LocalDate enrollmentDate, String enrollmentStatus) {
        this.studentId = studentId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
        this.enrollmentDate = enrollmentDate;
        this.enrollmentStatus = enrollmentStatus;
    }
    
    // Getters and Setters
    
    /**
     * Gets the student ID.
     * @return the student ID
     */
    public Long getStudentId() {
        return studentId;
    }
    
    /**
     * Sets the student ID.
     * @param studentId the student ID to set
     */
    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }
    
    /**
     * Gets the first name.
     * @return the first name
     */
    public String getFirstName() {
        return firstName;
    }
    
    /**
     * Sets the first name.
     * @param firstName the first name to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    /**
     * Gets the last name.
     * @return the last name
     */
    public String getLastName() {
        return lastName;
    }
    
    /**
     * Sets the last name.
     * @param lastName the last name to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    /**
     * Gets the email address.
     * @return the email address
     */
    public String getEmail() {
        return email;
    }
    
    /**
     * Sets the email address.
     * @param email the email address to set
     */
    public void setEmail(String email) {
        this.email = email;
    }
    
    /**
     * Gets the phone number.
     * @return the phone number
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }
    
    /**
     * Sets the phone number.
     * @param phoneNumber the phone number to set
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    
    /**
     * Gets the date of birth.
     * @return the date of birth
     */
    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }
    
    /**
     * Sets the date of birth.
     * @param dateOfBirth the date of birth to set
     */
    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
    
    /**
     * Gets the address.
     * @return the address
     */
    public String getAddress() {
        return address;
    }
    
    /**
     * Sets the address.
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }
    
    /**
     * Gets the city.
     * @return the city
     */
    public String getCity() {
        return city;
    }
    
    /**
     * Sets the city.
     * @param city the city to set
     */
    public void setCity(String city) {
        this.city = city;
    }
    
    /**
     * Gets the state.
     * @return the state
     */
    public String getState() {
        return state;
    }
    
    /**
     * Sets the state.
     * @param state the state to set
     */
    public void setState(String state) {
        this.state = state;
    }
    
    /**
     * Gets the zip code.
     * @return the zip code
     */
    public String getZipCode() {
        return zipCode;
    }
    
    /**
     * Sets the zip code.
     * @param zipCode the zip code to set
     */
    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }
    
    /**
     * Gets the enrollment date.
     * @return the enrollment date
     */
    public LocalDate getEnrollmentDate() {
        return enrollmentDate;
    }
    
    /**
     * Sets the enrollment date.
     * @param enrollmentDate the enrollment date to set
     */
    public void setEnrollmentDate(LocalDate enrollmentDate) {
        this.enrollmentDate = enrollmentDate;
    }
    
    /**
     * Gets the enrollment status.
     * @return the enrollment status
     */
    public String getEnrollmentStatus() {
        return enrollmentStatus;
    }
    
    /**
     * Sets the enrollment status.
     * @param enrollmentStatus the enrollment status to set
     */
    public void setEnrollmentStatus(String enrollmentStatus) {
        this.enrollmentStatus = enrollmentStatus;
    }
    
    /**
     * Returns the full name of the student.
     * @return the full name
     */
    public String getFullName() {
        return firstName + " " + lastName;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Objects.equals(studentId, student.studentId) &&
                Objects.equals(email, student.email);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(studentId, email);
    }
    
    @Override
    public String toString() {
        return "Student{" +
                "studentId=" + studentId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", zipCode='" + zipCode + '\'' +
                ", enrollmentDate=" + enrollmentDate +
                ", enrollmentStatus='" + enrollmentStatus + '\'' +
                '}';
    }
}
