package com.ingsoftware.contactmanager.services;

import com.ingsoftware.contactmanager.domain.contactDtos.ContactCreationDto;
import com.ingsoftware.contactmanager.domain.contactDtos.ContactInfoDto;
import com.ingsoftware.contactmanager.domain.contactDtos.ContactRequestDto;
import com.ingsoftware.contactmanager.domain.mappers.ContactMapper;
import com.ingsoftware.contactmanager.exceptions.ContactNotFoundException;
import com.ingsoftware.contactmanager.exceptions.InvalidEmailException;
import com.ingsoftware.contactmanager.exceptions.UserNotFoundException;
import com.ingsoftware.contactmanager.repositories.ContactRepository;
import com.ingsoftware.contactmanager.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class ContactService {

    private final ContactRepository contactRepository;
    private final UserRepository userRepository;
    private final ContactMapper contactMapper;

    public List<ContactInfoDto> findAllContacts() {

        List<ContactInfoDto> contactInfoDtoList = new ArrayList<>();

        for (var contacts : contactRepository.findAll()) {
            contactInfoDtoList.add(contactMapper.contactToContactInfoDto(contacts));
        }

        return contactInfoDtoList;

    }

    public List<ContactInfoDto> findAllUserContacts(UUID userID) {

        List<ContactInfoDto> allContacts = new ArrayList<>();

        var user = userRepository.findByGuid(userID).orElseThrow(() -> new UserNotFoundException("Invalid id"));

        for (var contact : user.getUsersContacts()) {
            allContacts.add(contactMapper.contactToContactInfoDto(contact));
        }

        return allContacts;

    }

    public String editContact(UUID contactId, ContactRequestDto contactRequestDto) {

        var contact = contactRepository.findByGuid(contactId).orElseThrow(()
                -> new ContactNotFoundException("Invalid contact id"));

        if (!EmailValidator.getInstance().isValid(contactRequestDto.getEmail())) {
            throw new InvalidEmailException("Invalid email");
        }

        contact.setFirstName(contactRequestDto.getFirstName());
        contact.setLastName(contactRequestDto.getLastName());
        contact.setEmail(contactRequestDto.getEmail());
        contact.setInfo(contactRequestDto.getInfo());
        contact.setAddress(contactRequestDto.getAddress());
        contact.setPhoneNumber(contactRequestDto.getPhoneNumber());
        contact.setUpdatedAt(LocalDateTime.now());

        return "Updated";

    }

    public String deleteContactById(UUID userId, UUID contactId) {

        var user = userRepository.findByGuid(userId).orElseThrow(() ->
                new UserNotFoundException("Invalid user id"));

        var contact = contactRepository.findByGuid(contactId).orElseThrow(()
                -> new ContactNotFoundException("Invalid contact id"));

        if (user.getUsersContacts().contains(contact)) {
            contactRepository.deleteContactById(contact.getId());
            user.getUsersContacts().remove(contact);
        }

        return "Deleted";

    }

    public String addContact(ContactCreationDto contactCreationDto, UUID id) {

        var user = userRepository.findByGuid(id).orElseThrow(() ->
                new UserNotFoundException("Invalid id"));

        var contact = contactMapper.contactCreationDtoToUser(contactCreationDto);

        contact.setUser(user);
        contactRepository.save(contact);
        user.getUsersContacts().add(contact);

        return "Created!";

    }

}
