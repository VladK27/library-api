package ru.karelin.project.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Setter
@Getter
@ToString
@NoArgsConstructor

@Entity
@Table(name = "Person")
public class Person {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    @NotNull(message = "Name can't be empty")
    @Size(min=1, max=30, message = "Name should be from 1 to 30 symbols")
    @Pattern(regexp = "[A-Z]\\w+", message = "Name should starts with big letter and contains only letters")
    private String name;

    @Column(name = "surname")
    @NotNull(message = "Surname can't be empty")
    @Size(min=1, max=30, message = "Surname should be from 1 to 30 symbols")
    @Pattern(regexp = "[A-Z]\\w+", message = "Surname should starts with big letter and contains only letters")
    private String surname;

    @Column(name = "year_of_birth")
    @NotNull(message = "Year can't be empty")
    @Min(value=1900, message = "Reader is too old for library")
    @Max(value=2023, message = "Reader is too young for library")
    private Integer yearOfBirth;

    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY)
    private List<Book> books;

    public Person(int id, String name, String surname, Integer yearOfBirth) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.yearOfBirth = yearOfBirth;
    }

    public String getFullName(){
        return name + " " + surname;
    }
}
