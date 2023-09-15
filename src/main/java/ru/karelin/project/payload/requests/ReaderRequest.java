package ru.karelin.project.payload.requests;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReaderRequest {

    @NotBlank(message = "Name can't be empty")
    @Size(min=1, max=30, message = "Name should be from 1 to 30 symbols")
    @Pattern(regexp = "[A-Z]\\w+", message = "Name should starts with big letter and contains only letters")
    private String name;

    @NotBlank(message = "Surname can't be empty")
    @Size(min=1, max=30, message = "Surname should be from 1 to 30 symbols")
    @Pattern(regexp = "[A-Z]\\w+", message = "Surname should starts with big letter and contains only letters")
    private String surname;

    @NotNull(message = "Year can't be empty")
    @Min(value=1900, message = "Year of birth must be after 1900")
    @Max(value=2023, message = "Year of birth must be before 2023")
    private Integer yearOfBirth;

    @NotBlank(message = "Email can't be empty")
    @Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = "Email not valid")
    private String email;
}

