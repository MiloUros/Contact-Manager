package com.ingsoftware.contactmanager.domain.userDtos;

import com.ingsoftware.contactmanager.domain.entitys.Contact;
import com.ingsoftware.contactmanager.domain.enums.Role;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class UserInfoDto {

    private String firstName;
    private String lastName;
    private String email;
    private Role role;
    private LocalDateTime createdAt;
    private List<Contact> usersContacts;

}
