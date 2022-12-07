package com.ingsoftware.contactmanager.domain.mappers;

import com.ingsoftware.contactmanager.domain.entitys.User;
import com.ingsoftware.contactmanager.domain.userDtos.UserRequestDto;
import com.ingsoftware.contactmanager.domain.userDtos.UserResponseDto;
import lombok.AccessLevel;
import lombok.Setter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

@Mapper
public abstract class UserMapper {

    @Autowired
    @Setter(AccessLevel.PACKAGE)
    PasswordEncoder passwordEncoder;

    @Mapping(target = "password", expression = "java(passwordEncoder.encode(userRequestDto.getPassword()))")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "usersContacts", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "guid", ignore = true)
    public abstract User userRegisterDtoToEntity(UserRequestDto userRequestDto);

    public abstract UserResponseDto userToUserInfoDto(User user);

}
