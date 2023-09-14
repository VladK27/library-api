package ru.karelin.project.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.karelin.project.exceptions.BadRequestException;
import ru.karelin.project.exceptions.ResourceNotFoundException;
import ru.karelin.project.payload.response.ApiExceptionResponse;
import ru.karelin.project.payload.response.ApiResponse;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class RestExceptionResolverController {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiResponse> resolveException(BadRequestException e){
        var response = e.getApiResponse();

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> resolveException(ResourceNotFoundException e){
        var response = e.getApiResponse();

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiExceptionResponse> resolveException(MethodArgumentNotValidException e){
        List<String> messages = new ArrayList<>(e.getFieldErrors()
                .stream().map(er -> er.getField() + ": " + er.getDefaultMessage())
                .toList());

        var response = new ApiExceptionResponse(messages);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
