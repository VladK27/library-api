package ru.karelin.project.exceptions;

import lombok.Getter;
import lombok.Setter;
import ru.karelin.project.payload.response.ApiResponse;

@Setter
@Getter
public class ResourceNotFoundException extends RuntimeException{
    private ApiResponse apiResponse;

    private String resourceName;
    private String fieldName;
    private String fieldValue;

    public ResourceNotFoundException(String resourceName, String fieldName, String fieldValue){
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;

        String message = String.format("%s with %s: %s not found",
                resourceName, fieldName, fieldValue);
        this.apiResponse = new ApiResponse(Boolean.FALSE, message);
    }
}
