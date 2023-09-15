package ru.karelin.project.payload.response;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReaderResponse {
    private Long id;
    private String name;
    private String surname;
    private Integer yearOfBirth;
    private String email;
}
