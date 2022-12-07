package com.ingsoftware.contactmanager.domain.contactDtos;

import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class ContactRequestDto {

    @Size(max = 20, message = "Must not exceed 20 characters")
    private String firstName;
    @Size(max = 20, message = "Must not exceed 20 characters")
    private String lastName;
    @Size(max = 20, message = "Must not exceed 20 characters")
    private String phoneNumber;
    @Size(max = 100, message = "Must not exceed 100 characters")
    private String address;
    @Size(max = 100, message = "Must not exceed 100 characters")
    private String info;
    @Column(unique = true)
    @NotBlank
    @Size(max = 40, message = "Email must not exceed 40 characters.")
    @Email(regexp = "^(.+)@(.+)$", message = "Invalid email format.")
    private String email;
    @Size(max = 25, message = "Must not exceed 25 characters")
    private String type;

}
