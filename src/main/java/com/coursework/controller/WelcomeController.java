package com.coursework.controller;

import com.coursework.model.Employee;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeController {

    @GetMapping("/")
    public String welcomePage(
            @AuthenticationPrincipal Employee employee
            ) {
        if (employee == null) return "Please log in";
        return "Hello, " + employee.getFirstName();
    }

}
