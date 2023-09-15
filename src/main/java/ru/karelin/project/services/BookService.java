package ru.karelin.project.services;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.karelin.project.exceptions.ResourceNotFoundException;
import ru.karelin.project.models.Book;
import ru.karelin.project.payload.response.PagedResponse;
import ru.karelin.project.repositories.BookRepository;
import ru.karelin.project.validators.PageValidator;
import static ru.karelin.project.utility.AppConstants.*;

import java.util.Optional;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final EntityManager entityManager;

    @Autowired
    public BookService(BookRepository bookRepository, EntityManager entityManager) {
        this.bookRepository = bookRepository;
        this.entityManager = entityManager;
    }

    public PagedResponse<Book> getAllBooks(int page, int size) {
        PageValidator.validate(page, size);

        Pageable pageable = PageRequest.of(page, size, Sort.Direction.ASC, "title");

        Page<Book> books = bookRepository.findAll(pageable);

        return new PagedResponse<>(
                books.getContent(),books.getNumber(), books.getSize(), books.getTotalPages(), books.getTotalElements());
    }

    public Book getBookById(Long id) {
        Optional<Book> book = bookRepository.findById(id);

        if(book.isEmpty()){
            throw new ResourceNotFoundException(BOOK, ID, id.toString());
        }

        book.get().setOverdue();
        return book.get();
    }

    public Book getBookByTitle(String title) {
        Optional<Book> book = bookRepository.findByTitle(title);

        if(book.isEmpty()){
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

        }catch (EntityNotFoundException e){
            throw new ResourceNotFoundException(BOOK, ID, book.getId().toString() );
        }
    }

    @Transactional
    public void deleteById(Long id) {
        if(!bookRepository.existsById(id)){
            throw new ResourceNotFoundException(BOOK, ID, id.toString());
        }

        bookRepository.deleteById(id);
    }
}
