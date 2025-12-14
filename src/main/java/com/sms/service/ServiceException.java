package com.sms.service;

/**
 * Custom exception for service layer operations.
 * 
 * @author SMS Development Team
 * @version 1.0
 * @since 2024
 */
public class ServiceException extends Exception {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * Constructs a new ServiceException with the specified detail message.
     * 
     * @param message the detail message
     */
    public ServiceException(String message) {
        super(message);
    }
    
    /**
     * Constructs a new ServiceException with the specified detail message and cause.
     * 
     * @param message the detail message
     * @param cause the cause of the exception
     */
    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }
    
    /**
     * Constructs a new ServiceException with the specified cause.
     * 
     * @param cause the cause of the exception
     */
    public ServiceException(Throwable cause) {
        super(cause);
    }
}
