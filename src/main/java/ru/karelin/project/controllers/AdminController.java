package ru.karelin.project.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.karelin.project.models.Employee;
import ru.karelin.project.models.EmployeeCredentials;
import ru.karelin.project.models.Role;
import ru.karelin.project.services.EmployeeCredentialsService;
import ru.karelin.project.services.EmployeeService;
import ru.karelin.project.validators.EmployeeCredentialsValidator;
import ru.karelin.project.validators.EmployeeValidator;
import ru.karelin.project.utility.PageConfig;

import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final EmployeeCredentialsValidator empCredentialsValidator;
    private final EmployeeValidator employeeValidator;
    private final EmployeeCredentialsService empCredentialsService;
    private final EmployeeService employeeService;

    @Autowired
    public AdminController(EmployeeCredentialsValidator empCredentialsValidator, EmployeeCredentialsService empCredentialsService, EmployeeValidator employeeValidator, EmployeeService employeeService) {
        this.empCredentialsValidator = empCredentialsValidator;
        this.empCredentialsService = empCredentialsService;
        this.employeeValidator = employeeValidator;
        this.employeeService = employeeService;
    }


}