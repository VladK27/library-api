package ru.karelin.project.services;

import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.karelin.project.exceptions.BadRequestException;
import ru.karelin.project.exceptions.ResourceNotFoundException;
import ru.karelin.project.models.Book;
import ru.karelin.project.models.Reader;
import ru.karelin.project.payload.response.ApiResponse;
import ru.karelin.project.payload.response.BookResponse;
import ru.karelin.project.payload.response.PagedResponse;
import ru.karelin.project.repositories.BookRepository;
import ru.karelin.project.repositories.ReaderRepository;
import ru.karelin.project.validators.PageValidator;

import static ru.karelin.project.utility.AppConstants.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final ReaderRepository readerRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public BookService(BookRepository bookRepository, ReaderRepository readerRepository, ModelMapper modelMapper) {
        this.bookRepository = bookRepository;
        this.readerRepository = readerRepository;
        this.modelMapper = modelMapper;
    }

    public Page<Book> getAllBooks(int page, int size) {
        PageValidator.validate(page, size);

        Pageable pageable = PageRequest.of(page, size, Sort.Direction.ASC, "title");

        return bookRepository.findAll(pageable);
    }

    public Book getBookById(Long id) {
        Optional<Book> book = bookRepository.findById(id);

        if (book.isEmpty()) {
            throw new ResourceNotFoundException(BOOK, ID, id.toString());
        }

        book.get().setOverdue();

        return book.get();
    }

    public Book getBookByTitle(String title) {
        Optional<Book> book = bookRepository.findByTitle(title);

        if (book.isEmpty()) {
            throw new ResourceNotFoundException(BOOK, TITLE, title);
        }

        return book.get();
    }

    @Transactional
    public Book addBook(Book book) {
        return bookRepository.save(book);
    }

    @Transactional
    public Book updateBook(Book book) {

        try {
            Book bookToUpdate = bookRepository.getReferenceById(book.getId());

            bookToUpdate.setTitle(book.getTitle());
            bookToUpdate.setAuthor(book.getAuthor());
            bookToUpdate.setYearOfPublish(book.getYearOfPublish());

            return bookRepository.save(bookToUpdate);

        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(BOOK, ID, book.getId().toString());
        }
    }

    @Transactional
    public void deleteById(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new ResourceNotFoundException(BOOK, ID, id.toString());
        }

        bookRepository.deleteById(id);
    }

    @Transactional
    public void updateOwner(Long id, Long ownerId) {
        Optional<Book> bookOptional = bookRepository.findById(id);
        Book bookToUpdate = bookOptional.orElseThrow(() -> new ResourceNotFoundException(BOOK, ID, id.toString()));

        if (bookToUpdate.hasOwner()) {
            var response = new ApiResponse(Boolean.FALSE, String.format("book with id: %d already has owner with id: %d",
                    id, bookToUpdate.getOwner().getId()));

            throw new BadRequestException(response);
        }

        if ( !readerRepository.existsById(ownerId)) {
            throw new ResourceNotFoundException(READER, ID, ownerId.toString());
        }
        Reader ownerRef = readerRepository.getReferenceById(ownerId);

        ownerRef.getBooks().add(bookToUpdate);
        bookToUpdate.setOwner(ownerRef);
        bookToUpdate.setDateOfIssue(new Date());

        bookRepository.save(bookToUpdate);
    }

    @Transactional
    public void returnBookToLibrary(Long id) {
        Optional<Book> bookOptional = bookRepository.findById(id);
        Book bookToUpdate = bookOptional.orElseThrow(() -> new ResourceNotFoundException(BOOK, ID, id.toString()));

        bookToUpdate.setOwner(null);
        bookToUpdate.setDateOfIssue(null);

        bookRepository.save(bookToUpdate);
    }

    public Page<Book> getBooksByOwnerId(Long ownerId, int page, int size) {
        if (!readerRepository.existsById(ownerId)) {
            throw new ResourceNotFoundException(READER, ID, ownerId.toString());
        }

        Reader ownerRef = readerRepository.getReferenceById(ownerId);

        Pageable pageable = PageRequest.of(page, size, Sort.Direction.ASC, TITLE);
        return bookRepository.findByOwner(ownerRef, pageable);
    }
}
