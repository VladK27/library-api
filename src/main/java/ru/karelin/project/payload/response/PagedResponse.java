package ru.karelin.project.payload.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Vlad on 11.09.2023.
 *
 * @author Vlad
 */
@Data
public class PagedResponse<T> {
    private List<T> content;
    private int page;
    private int totalPages;
    private int size;
    private long totalElements;

    public List<T> getContent() {
        return content == null ? Collections.emptyList() : new ArrayList<T>(content);
    }

    public PagedResponse() {
    }

    public PagedResponse(List<T> content, int page, int size, int totalPages, long totalElements) {
        this.content = content;
        this.page = page;
        this.totalPages = totalPages;
        this.size = size;
        this.totalElements = totalElements;
    }

}
