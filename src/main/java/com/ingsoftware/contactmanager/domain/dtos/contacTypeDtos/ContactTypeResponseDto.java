package com.ingsoftware.contactmanager.domain.dtos.contacTypeDtos;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ContactTypeResponseDto {

    private String value;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
