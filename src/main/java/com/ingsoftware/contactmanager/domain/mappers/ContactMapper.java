package com.ingsoftware.contactmanager.domain.mappers;
import com.ingsoftware.contactmanager.domain.contactDtos.ContactRequestDto;
import com.ingsoftware.contactmanager.domain.contactDtos.ContactResponseDto;
import com.ingsoftware.contactmanager.domain.entitys.Contact;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public abstract class ContactMapper {

    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "contactType", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "guid", ignore = true)
    public abstract Contact contactCreationDtoToUser(ContactRequestDto contactRequestDto);

    public abstract ContactResponseDto contactToContactInfoDto(Contact contact);

}
