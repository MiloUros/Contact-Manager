package com.ingsoftware.contactmanager.domain.userDtos;

import com.ingsoftware.contactmanager.domain.enums.Role;
import lombok.Data;


@Data
public class UserRegisterDto {

    private String email;
    private String password;
    private Role role;

}
