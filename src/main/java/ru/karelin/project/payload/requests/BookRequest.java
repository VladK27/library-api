package ru.karelin.project.payload.requests;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
    private String author;

    @Max(value = 2023, message = "Publication date cannot be later than 2023")
    private Integer yearOfPublish;
}
