package com.ingsoftware.contactmanager.domain.mappers;

import com.ingsoftware.contactmanager.domain.entitys.User;
import com.ingsoftware.contactmanager.domain.enums.Role;
import com.ingsoftware.contactmanager.domain.userDtos.UpdateUserRequestDto;
import com.ingsoftware.contactmanager.domain.userDtos.UserRequestDto;
import com.ingsoftware.contactmanager.domain.userDtos.UserResponseDto;
import lombok.AccessLevel;
import lombok.Setter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

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
    public abstract User userRequestDtoToEntity(UserRequestDto userRequestDto);

    public void updateEntityFromUpdateUserRequestDto(User user, UpdateUserRequestDto updateUserRequestDto) {
        if ( updateUserRequestDto == null ) {
            return;
        }

        if ( isNotEmpty(updateUserRequestDto.getPassword()) ) {
            user.setPassword( passwordEncoder.encode(updateUserRequestDto.getPassword()) );
        }

        if ( isNotEmpty( updateUserRequestDto.getFirstName() ) ) {
            user.setFirstName( updateUserRequestDto.getFirstName() );
        }

        if ( isNotEmpty( updateUserRequestDto.getLastName() ) ) {
            user.setLastName( updateUserRequestDto.getLastName() );
        }

        if ( isNotEmpty( updateUserRequestDto.getEmail() ) ) {
            user.setEmail( updateUserRequestDto.getEmail() );
        }

        if ( isNotEmpty( updateUserRequestDto.getRole() ) ) {
            user.setRole( Enum.valueOf( Role.class, updateUserRequestDto.getRole() ) );
        }

    }

    public abstract UserResponseDto userToUserInfoDto(User user);
    public abstract List<UserResponseDto> userToUserInfoDtoList(List<User> user);

    boolean isNotEmpty(String value) {
        return value != null && !value.isEmpty();
    }

}
