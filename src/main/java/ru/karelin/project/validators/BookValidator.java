package ru.karelin.project.validators;

import org.springframework.stereotype.Component;
import ru.karelin.project.exceptions.BadRequestException;
import ru.karelin.project.exceptions.ResourceNotFoundException;
import ru.karelin.project.models.Book;
import ru.karelin.project.payload.response.ApiResponse;
import ru.karelin.project.services.BookService;

@Component
public class BookValidator implements Validator<Book>{
    private final BookService bookService;

    public BookValidator(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    public void validate(Book item) {
        try{
            Book book = bookService.getBookByTitle(item.getTitle());

            if(!book.getId().equals(item.getId())){
                var apiResponse = new ApiResponse(Boolean.FALSE, "Book with this title already exists");
                throw new BadRequestException(apiResponse);
            }

        }catch(ResourceNotFoundException e){
            return;
        }
    }
}
