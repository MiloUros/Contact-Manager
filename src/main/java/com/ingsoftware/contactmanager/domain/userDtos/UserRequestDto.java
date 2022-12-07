package com.ingsoftware.contactmanager.domain.userDtos;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class UserRequestDto {

    @Size(max = 20, message = "Must not exceed 20 characters")
    private String firstName;

    @Size(max = 20, message = "Must not exceed 20 characters")
    private String lastName;

    @NotBlank
    @Size(max = 255, message = "Password must not exceed 255 characters.")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$",
            message = "Password must contain at least: 8 characters, one upper Case letter," +
                    " one lower case letter, one number and one special character.")
    private String password;

    @NotBlank
    @Size(max = 40, message = "Email must not exceed 40 characters.")
    @Email(regexp = "^(.+)@(.+)$", message = "Invalid email format.")
    private String email;

    @Pattern(regexp = "ADMIN|USER")
    @Size(max = 50, message = "Must not exceed 50 characters")
    private String role;
}
