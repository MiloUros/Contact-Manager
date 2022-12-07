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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public User findUser(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException(CommonErrorMessages.USER_NOT_FOUND));
    }

    public UserResponseDto findUser(UUID userUUID) {
        var user = userRepository.findByGuid(userUUID).orElseThrow(()
                -> new UserNotFoundException(CommonErrorMessages.USER_NOT_FOUND));
        return userMapper.userToUserInfoDto(user);
    }

    public UserResponseDto registerUser(UserRequestDto userRequestDto) {

        if (userRepository.existsByEmail(userRequestDto.getEmail())) {
            throw new EmailTakenException(CommonErrorMessages.EMAIL_TAKEN);
        }

        var user = userMapper.userRegisterDtoToEntity(userRequestDto);
        userRepository.save(user);
        return userMapper.userToUserInfoDto(user);
    }

    public List<UserResponseDto> findAllUsers() {
        List<UserResponseDto> userResponseDTOList = new ArrayList<>();
        for (var user : userRepository.findAll()) {
            userResponseDTOList.add(userMapper.userToUserInfoDto(user));
        }
        return userResponseDTOList;
    }

    public void deleteUserById(UUID id) {
        var user = userRepository.findByGuid(id).orElseThrow(()
                -> new UserNotFoundException(CommonErrorMessages.USER_NOT_FOUND));
        userRepository.delete(user);

    }

    public UserResponseDto editUser(UUID userId, UserRequestDto userRequestDto) {
        var user = userRepository.findByGuid(userId).orElseThrow(()
                -> new UserNotFoundException(CommonErrorMessages.INVALID_USER_GUID));

        if (userRepository.existsByEmail(userRequestDto.getEmail()) && !user.getEmail().equals(userRequestDto.getEmail())) {
            throw new EmailTakenException(CommonErrorMessages.EMAIL_TAKEN);
        }

        user.setFirstName(userRequestDto.getFirstName());
        user.setLastName(userRequestDto.getLastName());
        user.setPassword(passwordEncoder.encode(userRequestDto.getPassword()));
        user.setEmail(userRequestDto.getEmail());
        user.setUpdatedAt(LocalDateTime.now());

        return userMapper.userToUserInfoDto(user);
    }
}
