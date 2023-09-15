package ru.karelin.project.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.karelin.project.exceptions.BadRequestException;
import ru.karelin.project.models.Reader;
import ru.karelin.project.payload.response.ApiResponse;
import ru.karelin.project.services.ReaderService;

import java.util.Optional;

@Component
public class ReaderValidator implements Validator<Reader>{
    private final ReaderService readerService;

    @Autowired
    public ReaderValidator(ReaderService readerService) {
        this.readerService = readerService;
    }

    @Override
    public void validate(Reader item) {
        Optional<Reader> reader = readerService.getByEmail(item.getEmail());

        if( !(reader.isEmpty() || reader.get().getId().equals(item.getId())) ){
            ApiResponse apiResponse = new ApiResponse(
                    Boolean.FALSE, "reader with this email already exists");

            throw new BadRequestException(apiResponse);
        }
    }
}
