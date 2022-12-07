package com.ingsoftware.contactmanager.domain.mappers;

import com.ingsoftware.contactmanager.domain.contacTypeDtos.ContactTypeResponseDto;
import com.ingsoftware.contactmanager.domain.contacTypeDtos.ContactTypeRequestDto;
import com.ingsoftware.contactmanager.domain.entitys.ContactType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public abstract class ContactTypeMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "guid", ignore = true)
    @Mapping(target = "contactsPerContactType", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    public abstract ContactType contactTypeDtoToEntity(ContactTypeRequestDto contactTypeRequestDto);


    public abstract ContactTypeResponseDto entityToInfoDto(ContactType contactType);

}
