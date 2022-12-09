package com.ingsoftware.contactmanager.services;

import com.ingsoftware.contactmanager.CommonErrorMessages;
import com.ingsoftware.contactmanager.domain.entitys.User;
import com.ingsoftware.contactmanager.domain.mappers.UserMapper;
import com.ingsoftware.contactmanager.domain.userDtos.UserRequestDto;
import com.ingsoftware.contactmanager.domain.userDtos.UserResponseDto;
import com.ingsoftware.contactmanager.exceptions.EmailTakenException;
import com.ingsoftware.contactmanager.exceptions.UserNotFoundException;
import com.ingsoftware.contactmanager.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public User findUser(String email) {
        return findUserByEmail(email);
    }

    public UserResponseDto findUser(UUID userUUID) {
        var user = findUSerByGuid(userUUID);
        return userMapper.userToUserInfoDto(user);
    }

    public UserResponseDto registerUser(UserRequestDto userRequestDto) {

        if (userRepository.existsByEmail(userRequestDto.getEmail())) {
            throw new EmailTakenException(CommonErrorMessages.EMAIL_TAKEN);
        }

        var user = userMapper.userRequestDtoToEntity(userRequestDto);
        userRepository.save(user);
        return userMapper.userToUserInfoDto(user);
    }

    public List<UserResponseDto> findAllUsers() {
        return userMapper.userToUserInfoDtoList(userRepository.findAll());
    }

    public void deleteUserById(UUID userUUID) {
        var user = findUSerByGuid(userUUID);
        userRepository.delete(user);

    }

    public UserResponseDto editUser(UUID userUUID, UserRequestDto userRequestDto) {
        var user = findUSerByGuid(userUUID);

        if (userRepository.existsByEmail(userRequestDto.getEmail()) && !user.getEmail().equals(userRequestDto.getEmail())) {
            throw new EmailTakenException(CommonErrorMessages.EMAIL_TAKEN);
        }

        userMapper.updateEntityFromRequest(user, userRequestDto);

        return userMapper.userToUserInfoDto(user);
    }

    private User findUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(()
                -> new UserNotFoundException(CommonErrorMessages.USER_NOT_FOUND));
    }

    private User findUSerByGuid(UUID userUUID) {
        return userRepository.findUserByGuid(userUUID).orElseThrow(()
                -> new UserNotFoundException(CommonErrorMessages.INVALID_USER_GUID));
    }
}
