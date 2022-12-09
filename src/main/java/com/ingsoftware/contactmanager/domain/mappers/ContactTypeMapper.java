package com.ingsoftware.contactmanager.domain.mappers;

import com.ingsoftware.contactmanager.domain.contacTypeDtos.ContactTypeRequestDto;
import com.ingsoftware.contactmanager.domain.contacTypeDtos.ContactTypeResponseDto;
import com.ingsoftware.contactmanager.domain.entitys.ContactType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper
public abstract class ContactTypeMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "guid", ignore = true)
    @Mapping(target = "contactsPerContactType", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    public abstract ContactType contactTypeRequestDtoToEntity(ContactTypeRequestDto contactTypeRequestDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "guid", ignore = true)
    @Mapping(target = "contactsPerContactType", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    public abstract void updateEntityFromRequest(@MappingTarget ContactType contactType, ContactTypeRequestDto contactTypeRequestDto);

    public abstract ContactTypeResponseDto entityToContactTypeResponseDto(ContactType contactType);
    public abstract List<ContactTypeResponseDto> entityToContactTypeResponseDtoList(List<ContactType> contactType);

}
