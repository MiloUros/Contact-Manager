package com.ingsoftware.contactmanager.domain.contactDtos;

import lombok.Data;

@Data
public class ContactCreationDto {

    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;

}
