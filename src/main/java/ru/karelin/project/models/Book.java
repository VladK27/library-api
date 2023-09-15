package ru.karelin.project.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.Date;

@Data
@NoArgsConstructor
@Entity
@Table(name = "Books")
public class Book {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    @NotNull(message = "Name can't be empty")
    @NotEmpty(message = "Name can't be empty")
    @Size(min=2, max=200, message = "Title should be from 2 to 200 symbols")
    private String title;

    @Column(name = "author")
    @NotNull(message = "Name can't be empty")
    @NotEmpty(message = "Name can't be empty")
    @Pattern(regexp = "([A-Z][a-z]{2,30} )+[A-Z][a-z]{2,30}|[A-Z][a-z]{2,30}", message = "Name should starts with big letter and contains only letters")
    private String author;

    @Column(name = "year_of_publish")
    @NotNull(message = "Year of publish can't be empty")
    @Max(value=2023, message = "Year must be under 2023")
    private int yearOfPublish;

    @Column(name = "date_of_issue")
    @Temporal(TemporalType.DATE)
    private Date dateOfIssue;

    @Transient
    private boolean isOverdue;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Reader owner;

    public Book(String title, String author, int yearOfPublish){
        this.title = title;
        this.author = author;
        this.yearOfPublish = yearOfPublish;
    }

    public boolean hasOwner(){
        return getOwner() != null;
    }

    public void setOverdue(){
        if(dateOfIssue != null){
            Date currentDate = new Date();
            long millisInDay = 86_400_000;
            long difference = (currentDate.getTime() - dateOfIssue.getTime() ) / millisInDay;
            this.isOverdue = difference > 10;
        }
    }
}
