package com.ingsoftware.contactmanager.domain.contacTypeDtos;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class ContactTypeRequestDto {

    @NotBlank
    @Size(max = 20, message = "Value size must not exceed 20 characters")
    private String value;

}
