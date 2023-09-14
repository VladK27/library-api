package ru.karelin.project.validators;

import ru.karelin.project.exceptions.BadRequestException;
import ru.karelin.project.payload.response.ApiResponse;

public class PageValidator {
    public static void validate(int page, int size){
        if(page < 0){
            var apiResponse = new ApiResponse(false, "Page can't be less then 0");
            throw new BadRequestException(apiResponse);
        }
        if(size < 1){
            var apiResponse = new ApiResponse(false, "Size can't be less then 1");
            throw new BadRequestException(apiResponse);
        }
    }
}
