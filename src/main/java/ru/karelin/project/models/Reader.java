package ru.karelin.project.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@ToString
@NoArgsConstructor

@Entity
@Table(name = "Readers")
public class Reader {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    @NotNull(message = "Name can't be empty")
    @Size(min=1, max=30, message = "Name should be from 1 to 30 symbols")
    @Pattern(regexp = "[A-Z]\\w+", message = "Name should starts with big letter and contains only letters")
    private String name;

    @Column(name = "surname")
    @NotBlank(message = "Surname can't be empty")
    @Size(min=1, max=30, message = "Surname should be from 1 to 30 symbols")
    @Pattern(regexp = "[A-Z]\\w+", message = "Surname should starts with big letter and contains only letters")
    private String surname;

    @Column(name = "year_of_birth")
    @NotNull(message = "Year can't be empty")
    @Min(value=1900, message = "Year of birth must be after 1900")
    @Max(value=2023, message = "Year of birth must be before 2023")
    private Integer yearOfBirth;

    @NotBlank(message = "Email can't be empty")
    @Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = "Email not valid")
    @Column(name = "email")
    private String email;

    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY)
    private List<Book> books;

    public List<Book> getBooks() {
        return books == null ? new ArrayList<>() : books;
    }

    public Reader(Long id, String name, String surname, Integer yearOfBirth) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.yearOfBirth = yearOfBirth;
    }

    public Reader(Long id, String name, String surname){
        this.id = id;
        this.name = name;
        this.surname = surname;
    }
}
