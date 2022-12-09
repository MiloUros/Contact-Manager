package com.ingsoftware.contactmanager.services;

import com.ingsoftware.contactmanager.CommonErrorMessages;
import com.ingsoftware.contactmanager.domain.contactDtos.ContactRequestDto;
import com.ingsoftware.contactmanager.domain.contactDtos.ContactResponseDto;
import com.ingsoftware.contactmanager.domain.entitys.Contact;
import com.ingsoftware.contactmanager.domain.entitys.User;
import com.ingsoftware.contactmanager.domain.enums.Role;
import com.ingsoftware.contactmanager.domain.mappers.ContactMapper;
import com.ingsoftware.contactmanager.exceptions.ActionNotAllowedException;
import com.ingsoftware.contactmanager.exceptions.ContactNotFoundException;
import com.ingsoftware.contactmanager.exceptions.UserNotFoundException;
import com.ingsoftware.contactmanager.repositories.ContactRepository;
import com.ingsoftware.contactmanager.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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
        return contactMapper.contactToContactResponseDtoList(contactRepository.findAll());
    }

    public ContactResponseDto findContact(UUID contactUUID) {

        var contact = findContactByGuid(contactUUID);

        return contactMapper.contactToContactResponseDto(contact);
    }

    public List<ContactResponseDto> findAllUserContacts(UUID userUUID) {

        var user = findUSerByGuid(userUUID);

        return contactMapper.contactToContactResponseDtoList(user.getUsersContacts());

    }

    public ContactResponseDto editContact(UUID contactUUID, ContactRequestDto contactRequestDto, String userEmail) {

        var contact = findContactByGuid(contactUUID);

        var user = findUserByEmail(userEmail);

        if (!contact.getUser().equals(user)) {
            throw new ActionNotAllowedException(CommonErrorMessages.ACCESS_DENIED);
        }

        contactMapper.updateEntityFromRequest(contact, contactRequestDto);

        return contactMapper.contactToContactResponseDto(contact);
    }

    public void deleteContactById(String userEmail, UUID contactUUID) {

        var user = findUserByEmail(userEmail);

        var contact = findContactByGuid(contactUUID);

        if (!contact.getUser().equals(user) && user.getRole().equals(Role.USER)) {
            throw new ActionNotAllowedException(CommonErrorMessages.ACCESS_DENIED);
        }

        contactRepository.deleteById(contact.getId());

    }

    @Transactional
    public ContactResponseDto addContact(ContactRequestDto contactRequestDto, UUID userUUID) {

        var user = findUSerByGuid(userUUID);

        var contact = contactMapper.contactRequestDtoToEntity(contactRequestDto);

        contact.setUser(user);
        contactRepository.save(contact);

        return contactMapper.contactToContactResponseDto(contact);
    }

    private Contact findContactByGuid(UUID contactUUID) {
        return contactRepository.findContactByGuid(contactUUID).orElseThrow(()
                -> new ContactNotFoundException(CommonErrorMessages.INVALID_CONTACT_GUID));
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
