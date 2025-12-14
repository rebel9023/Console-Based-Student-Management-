package com.sms.dao;

/**
 * Custom exception for Data Access Object operations.
 * 
 * @author SMS Development Team
 * @version 1.0
 * @since 2024
 */
public class DaoException extends Exception {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * Constructs a new DaoException with the specified detail message.
     * 
     * @param message the detail message
     */
    public DaoException(String message) {
        super(message);
    }
    
    /**
     * Constructs a new DaoException with the specified detail message and cause.
     * 
     * @param message the detail message
     * @param cause the cause of the exception
     */
    public DaoException(String message, Throwable cause) {
        super(message, cause);
    }
    
    /**
     * Constructs a new DaoException with the specified cause.
     * 
     * @param cause the cause of the exception
     */
    public DaoException(Throwable cause) {
        super(cause);
    }
}
