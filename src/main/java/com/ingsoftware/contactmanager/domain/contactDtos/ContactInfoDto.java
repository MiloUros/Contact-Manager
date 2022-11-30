package com.ingsoftware.contactmanager.domain.contactDtos;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ContactInfoDto {

    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String type;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
