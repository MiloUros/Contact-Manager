package com.ingsoftware.contactmanager.services;

import com.ingsoftware.contactmanager.domain.userDtos.UserInfoDto;
import com.ingsoftware.contactmanager.domain.userDtos.UserRegisterDto;
import com.ingsoftware.contactmanager.domain.entitys.User;
import com.ingsoftware.contactmanager.domain.mappers.UserMapper;
import com.ingsoftware.contactmanager.domain.userDtos.UserRequestDto;
import com.ingsoftware.contactmanager.exceptions.*;
import com.ingsoftware.contactmanager.repositories.ContactRepository;
import com.ingsoftware.contactmanager.repositories.UserRepository;
import com.ingsoftware.contactmanager.security.PasswordValidation;
import lombok.AllArgsConstructor;
import org.apache.commons.validator.routines.EmailValidator;
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
    private final PasswordValidation passwordValidation;
    private final ContactRepository contactRepository;

    public String helloWorldApi() {
        return "Hello World";
    }

    public UserInfoDto registerUser(UserRegisterDto userRegisterDto) {
        if (!EmailValidator.getInstance().isValid(userRegisterDto.getEmail())) {
            throw new InvalidEmailException("Invalid Email");
        }

        if (!passwordValidation.validatePassword(userRegisterDto.getPassword()).isValid()) {
            throw new InvalidPasswordException("Invalid Password");
        }

        if (userRepository.existsByEmail(userRegisterDto.getEmail())) {
            throw new EmailTakenException("Email already taken.");
        }

        var user = userMapper.userRegisterDtoToEntity(userRegisterDto);
        userRepository.save(user);
        return userMapper.userToUserInfoDto(user);
    }

    public User userLogIn(String email, String password) {
        return userRepository.findByEmail(email).orElseThrow(()
                -> new UserNotFoundException("Invalid email"));
    }

    public List<UserInfoDto> findAllUsers() {
        List<UserInfoDto> userInfoDTOList = new ArrayList<>();
        for (var user : userRepository.findAll()) {
            userInfoDTOList.add(userMapper.userToUserInfoDto(user));
        }
        return userInfoDTOList;
    }

    public String deleteUserById(UUID id) {
        var user = userRepository.findByGuid(id).orElseThrow(()
                -> new UserNotFoundException("User not found"));
        contactRepository.deleteAllByUser(user);
        userRepository.delete(user);
        return "Deleted!";
    }

    public String editUser(UUID userId, UserRequestDto userRequestDto) {
        var user = userRepository.findByGuid(userId).orElseThrow(()
                -> new UserNotFoundException("Invalid user id"));

        if (!EmailValidator.getInstance().isValid(userRequestDto.getEmail())) {
            throw new InvalidEmailException("Invalid email");
        }

        if (!passwordValidation.validatePassword(userRequestDto.getPassword()).isValid()) {
            throw new InvalidPasswordException("Invalid password");
        }

        if (userRepository.existsByEmail(userRequestDto.getEmail())) {
            throw new EmailTakenException("Email taken, enter another one.");
        }

        user.setFirstName(userRequestDto.getFirstName());
        user.setLastName(userRequestDto.getLastName());
        user.setPassword(userRequestDto.getPassword());
        user.setEmail(userRequestDto.getEmail());
        user.setUpdatedAt(LocalDateTime.now());

        return "Updated";
    }
}
