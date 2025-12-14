package com.sms.controller;

import com.sms.model.StudentStatus;
import com.sms.service.StudentApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Web UI Controller - Handles Thymeleaf template rendering.
 */
@Controller
@RequestMapping("/")
public class WebUiController {
    
    @Autowired
    private StudentApiService studentApiService;
    
    /**
     * Dashboard page
     */
    @GetMapping({"", "/", "/dashboard"})
    public String dashboard(Model model) {
        var stats = studentApiService.getStatistics();
        model.addAttribute("stats", stats);
        model.addAttribute("students", studentApiService.getAllStudents(PageRequest.of(0, 10)));
        return "dashboard";
    }
    
    /**
     * Students list page
     */
    @GetMapping("/students")
    public String students(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model) {
        var studentPage = studentApiService.getAllStudents(PageRequest.of(page, size));
        model.addAttribute("students", studentPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", studentPage.getTotalPages());
        return "students/list";
    }
    
    /**
     * Add student form
     */
    @GetMapping("/students/add")
    public String addStudentForm(Model model) {
        model.addAttribute("statuses", StudentStatus.values());
        return "students/form";
    }
    
    /**
     * Edit student form
     */
    @GetMapping("/students/{id}/edit")
    public String editStudentForm(@PathVariable Integer id, Model model) {
        var student = studentApiService.getStudentById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid student ID"));
        model.addAttribute("student", student);
        model.addAttribute("statuses", StudentStatus.values());
        return "students/form";
    }
    
    /**
     * Login page
     */
    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
