package com.sms.ui;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sms.model.Student;
import com.sms.service.ServiceException;
import com.sms.service.StudentService;

/**
 * Console User Interface for Student Management System.
 * Provides menu-driven interaction with the system.
 * 
 * @author SMS Development Team
 * @version 1.0
 * @since 2024
 */
public class ConsoleUI {
    
    private StudentService studentService;
    private Scanner scanner;
    private boolean running;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final Logger logger = LoggerFactory.getLogger(ConsoleUI.class);
    
    /**
     * Constructor with StudentService dependency injection.
     * 
     * @param studentService the service to use
     */
    public ConsoleUI(StudentService studentService) {
        if (studentService == null) {
            throw new IllegalArgumentException("StudentService cannot be null");
        }
        this.studentService = studentService;
        this.scanner = new Scanner(System.in);
        this.running = true;
    }
    
    /**
     * Starts the console application main loop.
     */
    public void start() {
        System.out.println("Starting Student Management System Console UI");
        displayWelcomeMessage();
        
        while (running) {
            displayMainMenu();
            handleMenuChoice();
        }
        
        shutdown();
    }
    
    /**
     * Displays the welcome message.
     */
    private void displayWelcomeMessage() {
        clearScreen();
        System.out.println("╔════════════════════════════════════════════════════════╗");
        System.out.println("║    STUDENT MANAGEMENT SYSTEM - Console Application    ║");
        System.out.println("║           Version 1.0 - Welcome!                      ║");
        System.out.println("╚════════════════════════════════════════════════════════╝");
        System.out.println();
    }
    
    /**
     * Displays the main menu.
     */
    private void displayMainMenu() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("MAIN MENU");
        System.out.println("=".repeat(50));
        System.out.println("1. Add New Student");
        System.out.println("2. View All Students");
        System.out.println("3. Search Student");
        System.out.println("4. Update Student");
        System.out.println("5. Delete Student");
        System.out.println("6. Student Statistics");
        System.out.println("0. Exit");
        System.out.println("=".repeat(50));
        System.out.print("Enter your choice: ");
    }
    
    /**
     * Handles the user's menu choice.
     */
    private void handleMenuChoice() {
        String choice = scanner.nextLine().trim();
        
        try {
            switch (choice) {
                case "1":
                    addNewStudent();
                    break;
                case "2":
                    viewAllStudents();
                    break;
                case "3":
                    searchStudent();
                    break;
                case "4":
                    updateStudent();
                    break;
                case "5":
                    deleteStudent();
                    break;
                case "6":
                    showStatistics();
                    break;
                case "0":
                    running = false;
                    System.out.println("\nExiting application...");
                    break;
                default:
                    System.out.println("❌ Invalid choice. Please try again.");
            }
        } catch (Exception e) {
            System.out.println("❌ An error occurred: " + e.getMessage());
        }
    }
    
    /**
     * Handles adding a new student.
     */
    private void addNewStudent() {
        clearScreen();
        System.out.println("\n" + "=".repeat(50));
        System.out.println("ADD NEW STUDENT");
        System.out.println("=".repeat(50));
        
        try {
            System.out.print("Enter first name: ");
            String firstName = scanner.nextLine().trim();
            
            System.out.print("Enter last name: ");
            String lastName = scanner.nextLine().trim();
            
            System.out.print("Enter email: ");
            String email = scanner.nextLine().trim();
            
            System.out.print("Enter phone number: ");
            String phoneNumber = scanner.nextLine().trim();
            
            // Optional fields
            System.out.print("Enter date of birth (yyyy-MM-dd) [Press Enter to skip]: ");
            String dobStr = scanner.nextLine().trim();
            LocalDate dob = null;
            if (!dobStr.isEmpty()) {
                try {
                    dob = LocalDate.parse(dobStr, DATE_FORMATTER);
                } catch (DateTimeParseException e) {
                    System.out.println("⚠️  Invalid date format. Skipping...");
                }
            }
            
            System.out.print("Enter address [Press Enter to skip]: ");
            String address = scanner.nextLine().trim();
            
            System.out.print("Enter city [Press Enter to skip]: ");
            String city = scanner.nextLine().trim();
            
            System.out.print("Enter state [Press Enter to skip]: ");
            String state = scanner.nextLine().trim();
            
            System.out.print("Enter zip code [Press Enter to skip]: ");
            String zipCode = scanner.nextLine().trim();
            
            // Create student
            Student student = studentService.addStudent(firstName, lastName, email, phoneNumber);
            
            // Update optional fields if provided
            if (dob != null) student.setDateOfBirth(dob);
            if (!address.isEmpty()) student.setAddress(address);
            if (!city.isEmpty()) student.setCity(city);
            if (!state.isEmpty()) student.setState(state);
            if (!zipCode.isEmpty()) student.setZipCode(zipCode);
            
            // Update the student with optional fields
            if (dob != null || !address.isEmpty() || !city.isEmpty() || !state.isEmpty() || !zipCode.isEmpty()) {
                studentService.updateStudent(student);
            }
            
            System.out.println("\n✅ Student added successfully!");
            System.out.println("Student ID: " + student.getStudentId());
            System.out.println("Name: " + student.getFullName());
            System.out.println("Email: " + student.getEmail());
            
            pause();
            
        } catch (ServiceException e) {
            System.out.println("❌ Error: " + e.getMessage());
            pause();
        }
    }
    
    /**
     * Handles viewing all students.
     */
    private void viewAllStudents() {
        clearScreen();
        System.out.println("\n" + "=".repeat(80));
        System.out.println("ALL STUDENTS");
        System.out.println("=".repeat(80));
        
        try {
            List<Student> students = studentService.getAllStudents();
            
            if (students.isEmpty()) {
                System.out.println("No students found in the system.");
            } else {
                System.out.printf("%-8s %-15s %-15s %-30s %-20s%n",
                    "ID", "First Name", "Last Name", "Email", "Status");
                System.out.println("-".repeat(80));
                
                for (Student student : students) {
                    System.out.printf("%-8d %-15s %-15s %-30s %-20s%n",
                        student.getStudentId(),
                        student.getFirstName(),
                        student.getLastName(),
                        student.getEmail(),
                        student.getEnrollmentStatus());
                }
            }
            
            System.out.println("=".repeat(80));
            System.out.println("Total students: " + students.size());
            
            pause();
            
        } catch (ServiceException e) {
            System.out.println("❌ Error: " + e.getMessage());
            logger.error("Error retrieving students", e);
            pause();
        }
    }
    
    /**
     * Handles searching for students.
     */
    private void searchStudent() {
        clearScreen();
        System.out.println("\n" + "=".repeat(50));
        System.out.println("SEARCH STUDENT");
        System.out.println("=".repeat(50));
        System.out.println("1. Search by ID");
        System.out.println("2. Search by First Name");
        System.out.println("3. Search by Last Name");
        System.out.println("4. Search by Email");
        System.out.print("Enter search option: ");
        
        String choice = scanner.nextLine().trim();
        
        try {
            switch (choice) {
                case "1":
                    searchById();
                    break;
                case "2":
                    searchByFirstName();
                    break;
                case "3":
                    searchByLastName();
                    break;
                case "4":
                    searchByEmail();
                    break;
                default:
                    System.out.println("❌ Invalid option.");
            }
        } catch (ServiceException e) {
            System.out.println("❌ Error: " + e.getMessage());
            logger.error("Error searching student", e);
        }
        
        pause();
    }
    
    /**
     * Searches student by ID.
     */
    private void searchById() throws ServiceException {
        System.out.print("Enter student ID: ");
        try {
            Long id = Long.parseLong(scanner.nextLine().trim());
            Optional<Student> student = studentService.getStudent(id);
            
            if (student.isPresent()) {
                displayStudentDetails(student.get());
            } else {
                System.out.println("❌ Student not found.");
            }
        } catch (NumberFormatException e) {
            System.out.println("❌ Invalid ID format.");
        }
    }
    
    /**
     * Searches students by first name.
     */
    private void searchByFirstName() throws ServiceException {
        System.out.print("Enter first name: ");
        String firstName = scanner.nextLine().trim();
        
        List<Student> students = studentService.searchByFirstName(firstName);
        displaySearchResults(students);
    }
    
    /**
     * Searches students by last name.
     */
    private void searchByLastName() throws ServiceException {
        System.out.print("Enter last name: ");
        String lastName = scanner.nextLine().trim();
        
        List<Student> students = studentService.searchByLastName(lastName);
        displaySearchResults(students);
    }
    
    /**
     * Searches student by email.
     */
    private void searchByEmail() throws ServiceException {
        System.out.print("Enter email: ");
        String email = scanner.nextLine().trim();
        
        Optional<Student> student = studentService.searchByEmail(email);
        
        if (student.isPresent()) {
            displayStudentDetails(student.get());
        } else {
            System.out.println("❌ Student not found.");
        }
    }
    
    /**
     * Displays search results.
     */
    private void displaySearchResults(List<Student> students) {
        if (students.isEmpty()) {
            System.out.println("❌ No students found matching your search.");
        } else {
            System.out.println("\nFound " + students.size() + " student(s):");
            System.out.println("-".repeat(80));
            System.out.printf("%-8s %-15s %-15s %-30s %-15s%n",
                "ID", "First Name", "Last Name", "Email", "Status");
            System.out.println("-".repeat(80));
            
            for (Student student : students) {
                System.out.printf("%-8d %-15s %-15s %-30s %-15s%n",
                    student.getStudentId(),
                    student.getFirstName(),
                    student.getLastName(),
                    student.getEmail(),
                    student.getEnrollmentStatus());
            }
        }
    }
    
    /**
     * Displays detailed student information.
     */
    private void displayStudentDetails(Student student) {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("STUDENT DETAILS");
        System.out.println("=".repeat(50));
        System.out.println("ID: " + student.getStudentId());
        System.out.println("Name: " + student.getFullName());
        System.out.println("Email: " + student.getEmail());
        System.out.println("Phone: " + student.getPhoneNumber());
        System.out.println("Date of Birth: " + (student.getDateOfBirth() != null ? 
            student.getDateOfBirth() : "Not provided"));
        System.out.println("Address: " + (student.getAddress() != null && !student.getAddress().isEmpty() ? 
            student.getAddress() : "Not provided"));
        System.out.println("City: " + (student.getCity() != null && !student.getCity().isEmpty() ? 
            student.getCity() : "Not provided"));
        System.out.println("State: " + (student.getState() != null && !student.getState().isEmpty() ? 
            student.getState() : "Not provided"));
        System.out.println("Zip Code: " + (student.getZipCode() != null && !student.getZipCode().isEmpty() ? 
            student.getZipCode() : "Not provided"));
        System.out.println("Enrollment Date: " + (student.getEnrollmentDate() != null ? 
            student.getEnrollmentDate() : "Not provided"));
        System.out.println("Status: " + student.getEnrollmentStatus());
        System.out.println("=".repeat(50));
    }
    
    /**
     * Handles updating a student.
     */
    private void updateStudent() {
        clearScreen();
        System.out.println("\n" + "=".repeat(50));
        System.out.println("UPDATE STUDENT");
        System.out.println("=".repeat(50));
        
        try {
            System.out.print("Enter student ID to update: ");
            Long id = Long.parseLong(scanner.nextLine().trim());
            
            Optional<Student> studentOpt = studentService.getStudent(id);
            
            if (!studentOpt.isPresent()) {
                System.out.println("❌ Student not found.");
                pause();
                return;
            }
            
            Student student = studentOpt.get();
            
            System.out.println("\nCurrent details: " + student.getFullName());
            System.out.print("Enter new first name [Current: " + student.getFirstName() + "]: ");
            String firstName = scanner.nextLine().trim();
            if (!firstName.isEmpty()) student.setFirstName(firstName);
            
            System.out.print("Enter new last name [Current: " + student.getLastName() + "]: ");
            String lastName = scanner.nextLine().trim();
            if (!lastName.isEmpty()) student.setLastName(lastName);
            
            System.out.print("Enter new email [Current: " + student.getEmail() + "]: ");
            String email = scanner.nextLine().trim();
            if (!email.isEmpty()) student.setEmail(email);
            
            System.out.print("Enter new phone number [Current: " + student.getPhoneNumber() + "]: ");
            String phoneNumber = scanner.nextLine().trim();
            if (!phoneNumber.isEmpty()) student.setPhoneNumber(phoneNumber);
            
            System.out.print("Enter new status [Current: " + student.getEnrollmentStatus() + "]: ");
            String status = scanner.nextLine().trim();
            if (!status.isEmpty()) student.setEnrollmentStatus(status);
            
            if (studentService.updateStudent(student)) {
                System.out.println("\n✅ Student updated successfully!");
            } else {
                System.out.println("❌ Failed to update student.");
            }
            
            pause();
            
        } catch (NumberFormatException e) {
            System.out.println("❌ Invalid ID format.");
            pause();
        } catch (ServiceException e) {
            System.out.println("❌ Error: " + e.getMessage());
            logger.error("Error updating student", e);
            pause();
        }
    }
    
    /**
     * Handles deleting a student.
     */
    private void deleteStudent() {
        clearScreen();
        System.out.println("\n" + "=".repeat(50));
        System.out.println("DELETE STUDENT");
        System.out.println("=".repeat(50));
        
        try {
            System.out.print("Enter student ID to delete: ");
            Long id = Long.parseLong(scanner.nextLine().trim());
            
            Optional<Student> studentOpt = studentService.getStudent(id);
            
            if (!studentOpt.isPresent()) {
                System.out.println("❌ Student not found.");
                pause();
                return;
            }
            
            Student student = studentOpt.get();
            System.out.println("\nStudent to delete: " + student.getFullName() + " (" + student.getEmail() + ")");
            System.out.print("Are you sure? (yes/no): ");
            
            String confirmation = scanner.nextLine().trim().toLowerCase();
            
            if (confirmation.equals("yes") || confirmation.equals("y")) {
                if (studentService.deleteStudent(id)) {
                    System.out.println("✅ Student deleted successfully!");
                } else {
                    System.out.println("❌ Failed to delete student.");
                }
            } else {
                System.out.println("Deletion cancelled.");
            }
            
            pause();
            
        } catch (NumberFormatException e) {
            System.out.println("❌ Invalid ID format.");
            pause();
        } catch (ServiceException e) {
            System.out.println("❌ Error: " + e.getMessage());
            logger.error("Error deleting student", e);
            pause();
        }
    }
    
    /**
     * Displays system statistics.
     */
    private void showStatistics() {
        clearScreen();
        System.out.println("\n" + "=".repeat(50));
        System.out.println("SYSTEM STATISTICS");
        System.out.println("=".repeat(50));
        
        try {
            long totalStudents = studentService.getStudentCount();
            System.out.println("Total Students in System: " + totalStudents);
            
            List<Student> students = studentService.getAllStudents();
            long activeStudents = students.stream()
                .filter(s -> "ACTIVE".equalsIgnoreCase(s.getEnrollmentStatus()))
                .count();
            
            System.out.println("Active Students: " + activeStudents);
            System.out.println("Inactive Students: " + (totalStudents - activeStudents));
            
            System.out.println("=".repeat(50));
            
            pause();
            
        } catch (ServiceException e) {
            System.out.println("❌ Error: " + e.getMessage());
            logger.error("Error retrieving statistics", e);
            pause();
        }
    }
    
    /**
     * Clears the console screen.
     */
    private void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
    
    /**
     * Pauses execution until user presses Enter.
     */
    private void pause() {
        System.out.print("\nPress Enter to continue...");
        scanner.nextLine();
    }
    
    /**
     * Shuts down the application.
     */
    private void shutdown() {
        scanner.close();
        logger.info("Student Management System closed");
    }
}
