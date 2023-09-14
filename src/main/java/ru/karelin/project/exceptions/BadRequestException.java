package ru.karelin.project.exceptions;

import lombok.Getter;
import ru.karelin.project.payload.response.ApiResponse;

@Getter
public class BadRequestException extends RuntimeException {
    private ApiResponse apiResponse;

    public BadRequestException(ApiResponse apiResponse){
        this.apiResponse = apiResponse;
    }
}
