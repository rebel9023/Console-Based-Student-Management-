package com.sms.util;

import java.time.LocalDate;
import java.time.Period;
import java.util.regex.Pattern;

/**
 * Utility class for input validation.
 * Provides static methods for validating student data.
 * 
 * @author SMS Development Team
 * @version 1.0
 * @since 2024
 */
public class ValidationUtil {
    
    // Validation patterns
    private static final Pattern EMAIL_PATTERN = 
        Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
    
    private static final Pattern NAME_PATTERN = 
        Pattern.compile("^[a-zA-Z\\s'-]{2,50}$");
    
    private static final Pattern PHONE_PATTERN = 
        Pattern.compile("^[\\d\\-\\+\\s\\(\\)]{10,}$");
    
    private static final Pattern ZIP_CODE_PATTERN = 
        Pattern.compile("^\\d{5}(-\\d{4})?$");
    
    /**
     * Validates a name field.
     * 
     * @param name the name to validate
     * @return true if valid
     */
    public static boolean isValidName(String name) {
        return name != null && !name.trim().isEmpty() && 
               NAME_PATTERN.matcher(name.trim()).matches();
    }
    
    /**
     * Validates an email address.
     * 
     * @param email the email to validate
     * @return true if valid
     */
    public static boolean isValidEmail(String email) {
        return email != null && !email.trim().isEmpty() &&
               email.length() <= 100 &&
               EMAIL_PATTERN.matcher(email.trim()).matches();
    }
    
    /**
     * Validates a phone number.
     * 
     * @param phone the phone to validate
     * @return true if valid
     */
    public static boolean isValidPhone(String phone) {
        if (phone == null || phone.trim().isEmpty()) {
            return false;
        }
        
        String cleanPhone = phone.replaceAll("[\\D]", "");
        return cleanPhone.length() >= 10;
    }
    
    /**
     * Validates a zip code.
     * 
     * @param zipCode the zip code to validate
     * @return true if valid
     */
    public static boolean isValidZipCode(String zipCode) {
        if (zipCode == null || zipCode.trim().isEmpty()) {
            return false;
        }
        
        return ZIP_CODE_PATTERN.matcher(zipCode.trim()).matches();
    }
    
    /**
     * Validates a date of birth.
     * Ensures person is at least 16 years old.
     * 
     * @param dateOfBirth the date of birth to validate
     * @return true if valid
     */
    public static boolean isValidDateOfBirth(LocalDate dateOfBirth) {
        if (dateOfBirth == null) {
            return true; // Optional field
        }
        
        // Check date is not in future
        if (dateOfBirth.isAfter(LocalDate.now())) {
            return false;
        }
        
        // Check person is at least 16 years old
        Period age = Period.between(dateOfBirth, LocalDate.now());
        return age.getYears() >= 16;
    }
    
    /**
     * Validates enrollment status.
     * 
     * @param status the status to validate
     * @return true if valid
     */
    public static boolean isValidEnrollmentStatus(String status) {
        if (status == null || status.trim().isEmpty()) {
            return false;
        }
        
        String upper = status.trim().toUpperCase();
        return upper.equals("ACTIVE") || upper.equals("INACTIVE") || upper.equals("SUSPENDED");
    }
    
    /**
     * Checks if string is null or empty.
     * 
     * @param str the string to check
     * @return true if null or empty
     */
    public static boolean isEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }
    
    /**
     * Checks if string is not empty.
     * 
     * @param str the string to check
     * @return true if not empty
     */
    public static boolean isNotEmpty(String str) {
        return str != null && !str.trim().isEmpty();
    }
    
    /**
     * Trims and normalizes whitespace in string.
     * 
     * @param str the string to normalize
     * @return normalized string or empty if null
     */
    public static String normalize(String str) {
        return str == null ? "" : str.trim();
    }
    
    /**
     * Converts string to title case.
     * Example: "john doe" -> "John Doe"
     * 
     * @param str the string to convert
     * @return title case string
     */
    public static String toTitleCase(String str) {
        if (isEmpty(str)) {
            return str;
        }
        
        String[] words = str.split("\\s+");
        StringBuilder result = new StringBuilder();
        
        for (String word : words) {
            if (result.length() > 0) {
                result.append(" ");
            }
            result.append(word.substring(0, 1).toUpperCase())
                  .append(word.substring(1).toLowerCase());
        }
        
        return result.toString();
    }
    
    /**
     * Validates ID (positive number).
     * 
     * @param id the id to validate
     * @return true if valid
     */
    public static boolean isValidId(Long id) {
        return id != null && id > 0;
    }
    
    /**
     * Validates age based on date of birth.
     * 
     * @param dateOfBirth the date of birth
     * @param minAge minimum required age
     * @return true if person is at least minAge years old
     */
    public static boolean isValidAge(LocalDate dateOfBirth, int minAge) {
        if (dateOfBirth == null) {
            return false;
        }
        
        Period age = Period.between(dateOfBirth, LocalDate.now());
        return age.getYears() >= minAge;
    }
    
    /**
     * Gets the age from date of birth.
     * 
     * @param dateOfBirth the date of birth
     * @return age in years, or -1 if invalid
     */
    public static int getAge(LocalDate dateOfBirth) {
        if (dateOfBirth == null || dateOfBirth.isAfter(LocalDate.now())) {
            return -1;
        }
        
        return Period.between(dateOfBirth, LocalDate.now()).getYears();
    }
}
