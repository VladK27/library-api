package ru.karelin.project.controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.karelin.project.models.Book;
import ru.karelin.project.models.Reader;
import ru.karelin.project.payload.requests.BookRequest;
import ru.karelin.project.payload.requests.UpdateOwnerRequest;
import ru.karelin.project.payload.response.ApiResponse;
import ru.karelin.project.payload.response.BookResponse;
import ru.karelin.project.payload.response.PagedResponse;
import ru.karelin.project.payload.response.ReaderResponse;
import ru.karelin.project.services.BookService;
import ru.karelin.project.validators.BookValidator;

import java.util.List;

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
    public ResponseEntity<PagedResponse<BookResponse>> getAllBooks(
            @RequestParam(name = "page", required = false, defaultValue = DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(name = "size", required = false, defaultValue = DEFAULT_PAGE_SIZE) int size
    ) {
        Page<Book> books = bookService.getAllBooks(page, size);
        List<BookResponse> content = books.getContent().stream()
                .map(b -> modelMapper.map(b, BookResponse.class))
                .toList();

        var response = new PagedResponse<>(content,
                books.getNumber(), books.getSize(), books.getTotalPages(), books.getTotalElements());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookResponse> getBookById(@PathVariable(ID) Long id) {
        Book book = bookService.getBookById(id);
        var response = modelMapper.map(book, BookResponse.class);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<BookResponse> addBook(@Valid @RequestBody BookRequest bookRequest) {
        Book book = modelMapper.map(bookRequest, Book.class);
        bookValidator.validate(book);

        Book newBook = bookService.addBook(book);

        BookResponse response = modelMapper.map(newBook, BookResponse.class);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookResponse> updateBook(
            @Valid @RequestBody BookRequest bookRequest,
            @PathVariable(ID) Long id) {
        Book book = modelMapper.map(bookRequest, Book.class);
        book.setId(id);
        bookValidator.validate(book);

        Book newBook = bookService.updateBook(book);
        var response = modelMapper.map(newBook, BookResponse.class);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteBook(@PathVariable(ID) Long id) {
        bookService.deleteById(id);

        var response = new ApiResponse(Boolean.TRUE, "book with id: " + id + " has been deleted");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse> updateBookOwner(
            @PathVariable("id") Long id,
            @RequestBody UpdateOwnerRequest owner) {
        String message;

        if (owner.getOwnerId() == null) {
            bookService.returnBookToLibrary(id);
            message = "book with id:" + id + " has been successfully returned to library";
        } else {
            bookService.updateOwner(id, owner.getOwnerId());
            message = "book with id:" + id + " has been successfully issued to a reader with id:" + owner.getOwnerId();
        }

        ApiResponse apiResponse = new ApiResponse(Boolean.TRUE, message);

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

}