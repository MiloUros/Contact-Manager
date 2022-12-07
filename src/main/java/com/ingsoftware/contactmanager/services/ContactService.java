package com.ingsoftware.contactmanager.services;

import com.ingsoftware.contactmanager.CommonErrorMessages;
import com.ingsoftware.contactmanager.domain.contactDtos.ContactResponseDto;
import com.ingsoftware.contactmanager.domain.contactDtos.ContactRequestDto;
import com.ingsoftware.contactmanager.domain.mappers.ContactMapper;
import com.ingsoftware.contactmanager.exceptions.ActionNotAllowedException;
import com.ingsoftware.contactmanager.exceptions.ContactNotFoundException;
import com.ingsoftware.contactmanager.exceptions.InvalidEmailException;
import com.ingsoftware.contactmanager.exceptions.UserNotFoundException;
import com.ingsoftware.contactmanager.repositories.ContactRepository;
import com.ingsoftware.contactmanager.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
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

    public List<ContactResponseDto> findAllContacts() {

        List<ContactResponseDto> contactResponseDtoList = new ArrayList<>();

        for (var contacts : contactRepository.findAll()) {
            contactResponseDtoList.add(contactMapper.contactToContactInfoDto(contacts));
        }

        return contactResponseDtoList;

    }

    public ContactResponseDto findContact(UUID contactID) {

        var contact = contactRepository.findByGuid(contactID).orElseThrow(()
                -> new ContactNotFoundException(CommonErrorMessages.CONTACT_NOT_FOUND));

        return contactMapper.contactToContactInfoDto(contact);
    }

    public List<ContactResponseDto> findAllUserContacts(UUID userID) {

        List<ContactResponseDto> allContacts = new ArrayList<>();

        var user = userRepository.findByGuid(userID).orElseThrow(() -> new UserNotFoundException(CommonErrorMessages.INVALID_USER_GUID));

        for (var contact : user.getUsersContacts()) {
            allContacts.add(contactMapper.contactToContactInfoDto(contact));
        }

        return allContacts;

    }

    public ContactResponseDto editContact(UUID contactId, ContactRequestDto contactRequestDto, String userEmail) {

        var contact = contactRepository.findByGuid(contactId).orElseThrow(()
                -> new ContactNotFoundException(CommonErrorMessages.INVALID_CONTACT_GUID));

        var user = userRepository.findByEmail(userEmail).orElseThrow(()
                -> new UserNotFoundException(CommonErrorMessages.USER_NOT_FOUND));

        if (!user.getUsersContacts().contains(contact)){
            throw new ActionNotAllowedException(CommonErrorMessages.ACCESS_DENIED);
        }

        contact.setFirstName(contactRequestDto.getFirstName());
        contact.setLastName(contactRequestDto.getLastName());
        contact.setEmail(contactRequestDto.getEmail());
        contact.setInfo(contactRequestDto.getInfo());
        contact.setAddress(contactRequestDto.getAddress());
        contact.setPhoneNumber(contactRequestDto.getPhoneNumber());
        contact.setUpdatedAt(LocalDateTime.now());

        return contactMapper.contactToContactInfoDto(contact);
    }

    public void deleteContactById(String email, UUID contactId) {

        var user = userRepository.findByEmail(email).orElseThrow(() ->
                new UserNotFoundException(CommonErrorMessages.INVALID_USER_GUID));

        var contact = contactRepository.findByGuid(contactId).orElseThrow(()
                -> new ContactNotFoundException(CommonErrorMessages.INVALID_CONTACT_GUID));

        if (!user.getUsersContacts().contains(contact)) {
            throw new InvalidEmailException(CommonErrorMessages.ACCESS_DENIED);
        }

        contactRepository.deleteById(contact.getId());
        user.getUsersContacts().remove(contact);

    }

    public ContactResponseDto addContact(ContactRequestDto contactRequestDto, UUID id) {

        var user = userRepository.findByGuid(id).orElseThrow(() ->
                new UserNotFoundException(CommonErrorMessages.INVALID_USER_GUID));

        var contact = contactMapper.contactCreationDtoToUser(contactRequestDto);

        contact.setUser(user);
        contactRepository.save(contact);
        user.getUsersContacts().add(contact);

        return contactMapper.contactToContactInfoDto(contact);
    }

}
