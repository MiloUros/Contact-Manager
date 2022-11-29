package com.ingsoftware.contactmanager.domain.contactDtos;

import lombok.Data;

@Data
public class ContactRequestDto {

    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String address;
    private String info;
    private String email;

}
