package ru.karelin.project.payload.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ApiExceptionResponse {
    private List<String> messages;

    public ApiExceptionResponse(List<String> messages){
        this.messages = messages;
    }
}
