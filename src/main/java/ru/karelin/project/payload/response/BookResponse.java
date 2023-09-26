package ru.karelin.project.payload.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@JsonPropertyOrder({"id", "title", "author", "yearOfPublish", "dateOfIssue", "overdue", "owner"})
@Data
@NoArgsConstructor
public class BookResponse {
    private Long id;
    private String title;
    private String author;
    private int yearOfPublish;
    private Date dateOfIssue;
    private boolean isOverdue;
    private ReaderResponse owner;

    public BookResponse(Long id, String title, String author, int yearOfPublish, Date dateOfIssue, boolean isOverdue) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.yearOfPublish = yearOfPublish;
        this.dateOfIssue = dateOfIssue;
        this.isOverdue = isOverdue;
    }
}
