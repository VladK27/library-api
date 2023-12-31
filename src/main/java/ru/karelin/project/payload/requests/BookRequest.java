package ru.karelin.project.payload.requests;

import jakarta.validation.constraints.*;
import lombok.Data;

/**
 * Created by Vlad on 10.09.2023.
 *
 * @author Vlad
 */
@Data
public class BookRequest {

    @NotBlank(message = "Title can't be empty")
    @Size(min=4, message = "Title must be minimum 2 characters")
    private String title;

    @NotBlank(message = "Author name can't be empty")
    @Size(min=4, message = "Author name must be minimum 2 characters")
    @Pattern(regexp = "([A-Z][a-z]{2,30} )+[A-Z][a-z]{2,30}|[A-Z][a-z]{2,30}", message = "Name should starts with big letter and contains only letters")
    private String author;

    @Max(value = 2023, message = "Publication date cannot be later than 2023")
    @Min(value = 0, message = "Year must be more than zero")
    private Integer yearOfPublish;
}
