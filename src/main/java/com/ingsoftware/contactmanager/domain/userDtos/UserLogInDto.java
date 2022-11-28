package com.ingsoftware.contactmanager.domain.userDtos;

import lombok.Data;

@Data
public class UserLogInDto {

    private String email;
    private String password;

}
