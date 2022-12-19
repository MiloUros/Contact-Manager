package com.ingsoftware.contactmanager.domain.mappers;

import com.ingsoftware.contactmanager.domain.dtos.contacTypeDtos.ContactTypeRequestDto;
import com.ingsoftware.contactmanager.domain.dtos.contacTypeDtos.ContactTypeResponseDto;
import com.ingsoftware.contactmanager.domain.dtos.contacTypeDtos.UpdateContactTypeRequestDto;
import com.ingsoftware.contactmanager.domain.entitys.ContactType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public abstract class ContactTypeMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "guid", ignore = true)
    @Mapping(target = "contactsPerContactType", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    public abstract ContactType contactTypeRequestDtoToEntity(ContactTypeRequestDto contactTypeRequestDto);

    public void updateEntityFromRequest(ContactType contactType, UpdateContactTypeRequestDto updateContactTypeRequestDto) {

        if (isNotEmpty(updateContactTypeRequestDto.getValue())) {
            contactType.setValue(updateContactTypeRequestDto.getValue());
        }
    }

    public abstract ContactTypeResponseDto entityToContactTypeResponseDto(ContactType contactType);

    boolean isNotEmpty(String value) {
        return value != null && !value.isEmpty();
    }
}
