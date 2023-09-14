package ru.karelin.project.payload.requests;

import lombok.Data;

/**
 * Created by Vlad on 10.09.2023.
 *
 * @author Vlad
 */
@Data
public class BookUpdateOwnerRequest {
    private Long ownerId;
}
