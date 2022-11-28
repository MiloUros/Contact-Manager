package com.ingsoftware.contactmanager.services;

import com.ingsoftware.contactmanager.domain.contactDtos.ContactCreationDto;
import com.ingsoftware.contactmanager.domain.userDtos.UserInfoDto;
import com.ingsoftware.contactmanager.domain.userDtos.UserRegisterDto;
import com.ingsoftware.contactmanager.domain.entitys.User;
import com.ingsoftware.contactmanager.domain.mappers.ContactMapper;
import com.ingsoftware.contactmanager.domain.mappers.UserMapper;
import com.ingsoftware.contactmanager.exceptions.ContactNotFoundException;
import com.ingsoftware.contactmanager.exceptions.UserNotFoundException;
import com.ingsoftware.contactmanager.repositories.ContactRepository;
import com.ingsoftware.contactmanager.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final ContactMapper contactMapper;
    private final ContactRepository contactRepository;

    public String helloWorldApi() {
        return "Hello World";
    }

    public User registerUser(UserRegisterDto userRegisterDto) {
        return userRepository.save(userMapper.userRegisterDtoToEntity(userRegisterDto));
    }

    public User userLogIn(String email, String password) {

        return userRepository.findByEmail(email).orElseThrow(()
                -> new UserNotFoundException("Invalid email"));
    }

    public List<UserInfoDto> getAllUsers() {
        List<UserInfoDto> userInfoDTOList = new ArrayList<>();
        for (var user : userRepository.findAll()) {
            userInfoDTOList.add(userMapper.userToUserInfoDto(user));
        }
        return userInfoDTOList;
    }

    public String deleteContactById(UUID userId, UUID contactId) {
        var user = userRepository.findByIdentifier(userId).orElseThrow(() ->
                new UserNotFoundException("Invalid user id"));
        var contact = contactRepository.findByIdentifier(contactId).orElseThrow(()
                -> new ContactNotFoundException("Invalid contact id"));
        if (user.getUsersContacts().contains(contact)) {
            contactRepository.deleteContactById(contact.getId());
            user.getUsersContacts().remove(contact);
        }
        return "Deleted";
    }

    public String deleteUserById(UUID id) {
        userRepository.deleteUserByIdentifier(id);
        return "Deleted!";
    }

    public String addContact(ContactCreationDto contactCreationDto, UUID id) {
        var user = userRepository.findByIdentifier(id).orElseThrow(() -> new UserNotFoundException("Invalid id"));
        var contact = contactMapper.contactCreationDtoToUser(contactCreationDto);
        contact.setUser(user);
        contactRepository.save(contact);
        user.getUsersContacts().add(contact);
        return "Created!";
    }

}
