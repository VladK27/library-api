package ru.karelin.project.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.karelin.project.models.Book;
import ru.karelin.project.models.Person;
import ru.karelin.project.services.BookService;
import ru.karelin.project.services.PersonService;
import ru.karelin.project.utility.PageConfig;
import ru.karelin.project.validators.PersonValidator;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/people")
public class PersonController {

    private final PersonValidator personValidator;
    private final PersonService personService;
    private final BookService bookService;

    @Autowired
    public PersonController(PersonService personService, BookService bookService, PersonValidator personValidator) {
        this.personService = personService;
        this.bookService = bookService;
        this.personValidator = personValidator;
    }

}
