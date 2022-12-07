package com.ingsoftware.contactmanager.domain.contacTypeDtos;

import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class ContactTypeRequestDto {

    @Column(unique = true)
    @NotBlank
    @Size(max = 20, message = "Value size must not exceed 20 characters")
    private String value;

}
