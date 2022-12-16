package com.ingsoftware.contactmanager.domain.dtos.contacTypeDtos;

import lombok.Data;

import javax.validation.constraints.Size;

@Data
public class UpdateContactTypeRequestDto {

    @Size(max = 20, message = "Value size must not exceed 20 characters")
    private String value;

}
