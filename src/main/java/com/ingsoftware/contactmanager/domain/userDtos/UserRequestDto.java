package com.ingsoftware.contactmanager.domain.userDtos;

import lombok.Data;

@Data
public class UserRequestDto {
    private String firstName;
    private String lastName;
    private String password;
    private String email;
}
