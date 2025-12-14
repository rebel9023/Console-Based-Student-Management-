-- ============================================
-- Student Management System - Database Schema
-- Version: 1.0
-- Created: 2024
-- ============================================

-- Drop existing database if it exists
DROP DATABASE IF EXISTS `student_management_system`;

-- Create database
CREATE DATABASE `student_management_system` CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Use the database
USE `student_management_system`;

-- ============================================
-- Create Students Table
-- ============================================
CREATE TABLE `students` (
    `student_id` BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT 'Unique student identifier',
    
    -- Basic Information
    `first_name` VARCHAR(50) NOT NULL COMMENT 'First name of the student',
    `last_name` VARCHAR(50) NOT NULL COMMENT 'Last name of the student',
    `email` VARCHAR(100) NOT NULL UNIQUE COMMENT 'Unique email address',
    `phone_number` VARCHAR(20) COMMENT 'Contact phone number',
    
    -- Personal Information
    `date_of_birth` DATE COMMENT 'Date of birth of the student',
    
    -- Address Information
    `address` VARCHAR(255) COMMENT 'Street address',
    `city` VARCHAR(50) COMMENT 'City name',
    `state` VARCHAR(50) COMMENT 'State or province',
    `zip_code` VARCHAR(20) COMMENT 'Postal code',
    
    -- Enrollment Information
    `enrollment_date` DATE NOT NULL DEFAULT CURDATE() COMMENT 'Date when student enrolled',
    `enrollment_status` VARCHAR(20) NOT NULL DEFAULT 'ACTIVE' COMMENT 'Current enrollment status (ACTIVE, INACTIVE, SUSPENDED)',
    
    -- Metadata
    `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT 'Record creation timestamp',
    `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Last update timestamp',
    
    -- Indexes
    KEY `idx_email` (`email`),
    KEY `idx_first_name` (`first_name`),
    KEY `idx_last_name` (`last_name`),
    KEY `idx_enrollment_status` (`enrollment_status`),
    KEY `idx_enrollment_date` (`enrollment_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci
  COMMENT='Stores student information and enrollment details';

-- ============================================
-- Create Indexes for Performance
-- ============================================
CREATE INDEX idx_name_lookup ON `students` (`last_name`, `first_name`);
CREATE INDEX idx_active_students ON `students` (`enrollment_status`, `enrollment_date`);

-- ============================================
-- Sample Data (Optional)
-- ============================================
-- Insert sample students for testing
INSERT INTO `students` (
    `first_name`, `last_name`, `email`, `phone_number`, 
    `date_of_birth`, `address`, `city`, `state`, `zip_code`, 
    `enrollment_date`, `enrollment_status`
) VALUES
    (
        'John', 'Doe', 'john.doe@example.com', '555-0101',
        '2005-01-15', '123 Main St', 'Springfield', 'IL', '62701',
        CURDATE(), 'ACTIVE'
    ),
    (
        'Jane', 'Smith', 'jane.smith@example.com', '555-0102',
        '2005-03-20', '456 Oak Ave', 'Shelbyville', 'IL', '62702',
        CURDATE(), 'ACTIVE'
    ),
    (
        'Michael', 'Johnson', 'michael.johnson@example.com', '555-0103',
        '2005-05-10', '789 Elm St', 'Capital City', 'IL', '62703',
        CURDATE(), 'ACTIVE'
    ),
    (
        'Emily', 'Williams', 'emily.williams@example.com', '555-0104',
        '2005-07-25', '321 Pine Rd', 'Springfield', 'IL', '62701',
        DATE_SUB(CURDATE(), INTERVAL 30 DAY), 'INACTIVE'
    ),
    (
        'David', 'Brown', 'david.brown@example.com', '555-0105',
        '2005-09-12', '654 Cedar Ln', 'Shelbyville', 'IL', '62702',
        CURDATE(), 'ACTIVE'
    );

-- ============================================
-- Create View for Active Students
-- ============================================
CREATE VIEW `vw_active_students` AS
SELECT 
    `student_id`,
    `first_name`,
    `last_name`,
    `email`,
    `phone_number`,
    `enrollment_date`,
    `enrollment_status`
FROM `students`
WHERE `enrollment_status` = 'ACTIVE'
ORDER BY `last_name`, `first_name`;

-- ============================================
-- Verify Schema Creation
-- ============================================
-- This section can be run to verify the schema
-- SELECT COUNT(*) AS total_students FROM `students`;
-- DESCRIBE `students`;
-- SHOW INDEX FROM `students`;
