package com.ingsoftware.contactmanager.domain.userDtos;

import com.ingsoftware.contactmanager.domain.enums.Role;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserInfoDto {

    private String firstName;
    private String lastName;
    private String email;
    private Role role;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
