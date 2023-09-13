package ru.karelin.project.payload.response;

import lombok.Data;
import static ru.karelin.project.utility.AppConstants.*;

import java.time.LocalDateTime;
/**
 * Created by Vlad on 10.09.2023.
 *
 * @author Vlad
 */
@Data
public class BookResponse {
    private Long id;
    private String title;
    private String author;
    private Integer yearOfPublish;
    private LocalDateTime dateOfIssue;
    private boolean isOverdue;

    public void setOverdue(){
        if(dateOfIssue != null){
            this.isOverdue = LocalDateTime.now()
                    .minusDays(DAYS_BEFORE_OVERDUE)
                    .equals(dateOfIssue);
        }
    }
}
