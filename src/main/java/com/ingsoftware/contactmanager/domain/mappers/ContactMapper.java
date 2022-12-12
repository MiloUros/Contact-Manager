package com.ingsoftware.contactmanager.domain.mappers;

import com.ingsoftware.contactmanager.domain.dtos.contactDtos.ContactRequestDto;
import com.ingsoftware.contactmanager.domain.dtos.contactDtos.ContactResponseDto;
import com.ingsoftware.contactmanager.domain.entitys.Contact;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

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

    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "contactType", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "guid", ignore = true)
    public abstract void updateEntityFromRequest(@MappingTarget Contact contact, ContactRequestDto contactRequestDto);

    public abstract ContactResponseDto contactToContactResponseDto(Contact contact);
    public abstract List<ContactResponseDto> contactToContactResponseDtoList(List<Contact> contact);


}
