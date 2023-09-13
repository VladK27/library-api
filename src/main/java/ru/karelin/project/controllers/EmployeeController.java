package ru.karelin.project.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.karelin.project.models.Employee;
import ru.karelin.project.models.EmployeeCredentials;
import ru.karelin.project.security.EmployeeDetails;
import ru.karelin.project.services.EmployeeCredentialsService;
import ru.karelin.project.services.EmployeeService;
import ru.karelin.project.validators.EmployeeCredentialsValidator;
import ru.karelin.project.validators.EmployeeValidator;

import java.util.Optional;

@Controller
@RequestMapping("/employee")
public class EmployeeController {

    private final EmployeeCredentialsService employeeCredentialsService;
    private final EmployeeCredentialsValidator empCredentialsValidator;
    private final EmployeeService employeeService;
    private final EmployeeValidator employeeValidator;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public EmployeeController(EmployeeCredentialsService employeeCredentialsService, PasswordEncoder passwordEncoder, EmployeeCredentialsValidator empCredentialsValidator, EmployeeService employeeService, EmployeeValidator employeeValidator) {
        this.employeeCredentialsService = employeeCredentialsService;
        this.passwordEncoder = passwordEncoder;
        this.empCredentialsValidator = empCredentialsValidator;
        this.employeeService = employeeService;
        this.employeeValidator = employeeValidator;
    }

}
