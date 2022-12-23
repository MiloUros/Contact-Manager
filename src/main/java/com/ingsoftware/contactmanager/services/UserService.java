package com.ingsoftware.contactmanager.services;

import com.ingsoftware.contactmanager.CommonErrorMessages;
import com.ingsoftware.contactmanager.domain.dtos.CustomPageDto;
import com.ingsoftware.contactmanager.domain.dtos.userDtos.UpdateUserRequestDto;
import com.ingsoftware.contactmanager.domain.dtos.userDtos.UserRequestDto;
import com.ingsoftware.contactmanager.domain.dtos.userDtos.UserResponseDto;
import com.ingsoftware.contactmanager.domain.entitys.User;
import com.ingsoftware.contactmanager.domain.mappers.UserMapper;
import com.ingsoftware.contactmanager.exceptions.EmailTakenException;
import com.ingsoftware.contactmanager.exceptions.UserNotFoundException;
import com.ingsoftware.contactmanager.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
@AllArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private JavaMailSender javaMailSender;
    private Environment environment;
    private final Logger logger = Logger.getLogger(UserService.class.getName());

    @Transactional(readOnly = true)
    public User findUser(String email) {
        return findUserByEmail(email);
    }

    @Transactional(readOnly = true)
    public UserResponseDto findUser(UUID userUUID) {
        var user = findUSerByGuid(userUUID);
        return userMapper.userToUserInfoDto(user);
    }

    @Transactional(rollbackFor = {RuntimeException.class})
    public UserResponseDto createUser(UserRequestDto userRequestDto) {

        if (userRepository.existsByEmail(userRequestDto.getEmail())) {
            throw new EmailTakenException(CommonErrorMessages.EMAIL_TAKEN);
        }

        var user = userMapper.userRequestDtoToEntity(userRequestDto);
        userRepository.save(user);
        try {
            sendNotifications(user);
        } catch (MailException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
        }
        return userMapper.userToUserInfoDto(user);
    }

    @Transactional(readOnly = true)
    public CustomPageDto<UserResponseDto> findAllUsers(Pageable pageable) {
        var users = userRepository.findAll(pageable).map(userMapper::userToUserInfoDto);
        return new CustomPageDto<>(users.getContent(), pageable.getPageNumber(),
                pageable.getPageSize(), users.getTotalElements());
    }

    @Transactional(rollbackFor = {RuntimeException.class})
    public void deleteUser(UUID userUUID) {
        var user = findUSerByGuid(userUUID);
        userRepository.delete(user);

    }

    @Transactional(rollbackFor = {RuntimeException.class})
    public UserResponseDto updateUser(UUID userUUID, UpdateUserRequestDto updateUserRequestDto) {
        var user = findUSerByGuid(userUUID);

        if (userRepository.existsByEmail(updateUserRequestDto.getEmail()) && !user.getEmail().equals(updateUserRequestDto.getEmail())) {
            throw new EmailTakenException(CommonErrorMessages.EMAIL_TAKEN);
        }

        userMapper.updateEntityFromRequest(user, updateUserRequestDto);

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

    public void sendNotifications(User user) throws MailException {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(user.getEmail());
        mail.setFrom(environment.getProperty("spring.mail.username"));
        mail.setSubject("Registration confirmation.");
        mail.setText("Successfully registered.");

        javaMailSender.send(mail);
    }
}
