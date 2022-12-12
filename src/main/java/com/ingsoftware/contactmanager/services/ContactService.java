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

import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ContactService {

    private final ContactRepository contactRepository;
    private final UserRepository userRepository;
    private final ContactMapper contactMapper;

    @Transactional(readOnly = true)
    public List<ContactResponseDto> findAllContacts() {
        return contactMapper.contactToContactResponseDtoList(contactRepository.findAll());
    }

    @Transactional(readOnly = true)
    public ContactResponseDto findContact(UUID contactUUID) {

        var contact = findContactByGuid(contactUUID);

        return contactMapper.contactToContactResponseDto(contact);
    }

    @Transactional(readOnly = true)
    public List<ContactResponseDto> findAllUserContacts(User user) {

        return contactMapper.contactToContactResponseDtoList(user.getUsersContacts());

    }

    @Transactional(rollbackFor = {RuntimeException.class})
    public ContactResponseDto updateContact(UUID contactUUID, ContactRequestDto contactRequestDto, String userEmail) {

        var contact = findContactByGuid(contactUUID);

        var user = findUserByEmail(userEmail);

        if (!contact.getUser().equals(user)) {
            throw new ActionNotAllowedException(CommonErrorMessages.ACCESS_DENIED);
        }

        contactMapper.updateEntityFromRequest(contact, contactRequestDto);

        return contactMapper.contactToContactResponseDto(contact);
    }

    @Transactional(rollbackFor = {RuntimeException.class})
    public void deleteContact(String userEmail, UUID contactUUID) {

        var user = findUserByEmail(userEmail);

        var contact = findContactByGuid(contactUUID);

        if (contact.getUser().equals(user) || !user.getRole().equals(Role.ADMIN)) {
            contactRepository.deleteById(contact.getId());
        } else {
            throw new ActionNotAllowedException(CommonErrorMessages.ACCESS_DENIED);
        }
    }

    @Transactional(rollbackFor = {RuntimeException.class})
    public ContactResponseDto createContact(ContactRequestDto contactRequestDto, UUID userUUID) {

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

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(()
                -> new UserNotFoundException(CommonErrorMessages.USER_NOT_FOUND));
    }

    public User findUSerByGuid(UUID userUUID) {
        return userRepository.findUserByGuid(userUUID).orElseThrow(()
                -> new UserNotFoundException(CommonErrorMessages.INVALID_USER_GUID));
    }

}
