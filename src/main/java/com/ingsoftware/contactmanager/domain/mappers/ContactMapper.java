package com.ingsoftware.contactmanager.domain.mappers;

import com.ingsoftware.contactmanager.domain.dtos.contactDtos.UpdateContactRequestDto;
import com.ingsoftware.contactmanager.domain.dtos.contactDtos.ContactRequestDto;
import com.ingsoftware.contactmanager.domain.dtos.contactDtos.ContactResponseDto;
import com.ingsoftware.contactmanager.domain.entitys.Contact;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public abstract class ContactMapper {

    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "contactType", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "guid", ignore = true)
    public abstract Contact contactRequestDtoToEntity(ContactRequestDto contactRequestDto);

    public void updateEntityFromRequest(Contact contact, UpdateContactRequestDto updateContactRequestDto) {

        if (isNotEmpty(updateContactRequestDto.getFirstName())) {
            contact.setFirstName( updateContactRequestDto.getFirstName() );
        }

        if (isNotEmpty(updateContactRequestDto.getLastName())) {
            contact.setLastName( updateContactRequestDto.getLastName() );
        }

        if (isNotEmpty(updateContactRequestDto.getEmail())) {
            contact.setEmail( updateContactRequestDto.getEmail() );
        }

        if (isNotEmpty(updateContactRequestDto.getPhoneNumber())) {
            contact.setPhoneNumber( updateContactRequestDto.getPhoneNumber() );
        }

        if (isNotEmpty(updateContactRequestDto.getAddress())) {
            contact.setAddress( updateContactRequestDto.getAddress() );
        }

        if (isNotEmpty(updateContactRequestDto.getInfo())) {
            contact.setInfo( updateContactRequestDto.getInfo() );
        }

        if (isNotEmpty(updateContactRequestDto.getType())) {
            contact.setType( updateContactRequestDto.getType() );
        }

    }

    public abstract ContactResponseDto contactToContactResponseDto(Contact contact);

    boolean isNotEmpty(String value) {
        return value != null && !value.isEmpty();
    }
}
