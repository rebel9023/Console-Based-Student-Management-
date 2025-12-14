package com.sms.model;

/**
 * StudentStatus enum - Represents student enrollment status.
 */
public enum StudentStatus {
    ACTIVE("Active"),
    INACTIVE("Inactive"),
    SUSPENDED("Suspended"),
    GRADUATED("Graduated");
    
    private final String displayName;
    
    StudentStatus(String displayName) {
        this.displayName = displayName;
    }
    
    public String getDisplayName() {
        return displayName;
    }
}
