package com.ingsoftware.contactmanager.domain.mappers;

import com.ingsoftware.contactmanager.domain.contactDtos.ContactCreationDto;
import com.ingsoftware.contactmanager.domain.contactDtos.ContactInfoDto;
import com.ingsoftware.contactmanager.domain.entitys.Contact;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public abstract class ContactMapper {

    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "contactType", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "address", ignore = true)
    @Mapping(target = "info", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "identifier", ignore = true)
    public abstract Contact contactCreationDtoToUser(ContactCreationDto contactCreationDto);

    public abstract ContactInfoDto contactToContactInfoDto(Contact contact);

}
