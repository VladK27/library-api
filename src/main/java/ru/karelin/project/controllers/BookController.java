package ru.karelin.project.controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.karelin.project.models.Book;
import ru.karelin.project.payload.requests.BookRequest;
import ru.karelin.project.payload.response.ApiResponse;
import ru.karelin.project.payload.response.PagedResponse;
import ru.karelin.project.services.BookService;
import ru.karelin.project.validators.BookValidator;
import static ru.karelin.project.utility.AppConstants.*;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookValidator bookValidator;
    private final BookService bookService;

    private final ModelMapper modelMapper;

    @Autowired
    public BookController(BookService bookService, BookValidator bookValidator, ModelMapper modelMapper) {
        this.bookService = bookService;
        this.bookValidator = bookValidator;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public ResponseEntity<PagedResponse<Book>> getAllBooks(
            @RequestParam(name = "page", required = false, defaultValue = DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(name = "size", required = false, defaultValue = DEFAULT_PAGE_SIZE) int size
    ) {
        PagedResponse<Book> response = bookService.getAllBooks(page, size);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable(ID) Long id){
        Book response = bookService.getBookById(id);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Book> addBook(@Valid @RequestBody BookRequest bookRequest)
    {
        Book book = modelMapper.map(bookRequest, Book.class);
        bookValidator.validate(book);

        Book newBook = bookService.addBook(book);
        return new ResponseEntity<>(newBook, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(
            @Valid @RequestBody BookRequest bookRequest,
            @PathVariable(ID) Long id)
    {
        Book book = modelMapper.map(bookRequest, Book.class);
        book.setId(id);
        bookValidator.validate(book);

        Book newBook = bookService.updateBook(book);
        return new ResponseEntity<>(newBook, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteBook(@PathVariable(ID) Long id){
        bookService.deleteById(id);

        var response = new ApiResponse(Boolean.TRUE, "book with id: " + id + " has been deleted");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}