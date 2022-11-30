package com.ingsoftware.contactmanager.domain.mappers;

import com.ingsoftware.contactmanager.domain.entitys.User;
import com.ingsoftware.contactmanager.domain.userDtos.UserInfoDto;
import com.ingsoftware.contactmanager.domain.userDtos.UserLogInDto;
import com.ingsoftware.contactmanager.domain.userDtos.UserRegisterDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public abstract class UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "usersContacts", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "guid", ignore = true)
    public abstract User userRegisterDtoToEntity(UserRegisterDto userRegisterDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "firstName", ignore = true)
    @Mapping(target = "lastName", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "usersContacts", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "guid", ignore = true)
    public abstract User userLogInDtoToEntity(UserLogInDto userLogInDto);

    public abstract UserInfoDto userToUserInfoDto(User user);

}
