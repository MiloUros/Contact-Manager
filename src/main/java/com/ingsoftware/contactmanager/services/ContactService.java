package com.ingsoftware.contactmanager.services;

import com.ingsoftware.contactmanager.CommonErrorMessages;
import com.ingsoftware.contactmanager.domain.dtos.CustomPageDto;
import com.ingsoftware.contactmanager.domain.dtos.contactDtos.ContactRequestDto;
import com.ingsoftware.contactmanager.domain.dtos.contactDtos.ContactResponseDto;
import com.ingsoftware.contactmanager.domain.dtos.contactDtos.UpdateContactRequestDto;
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
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ContactService {

    private final ContactRepository contactRepository;
    private final UserRepository userRepository;
    private final ContactMapper contactMapper;

    @Transactional(readOnly = true)
    public CustomPageDto<ContactResponseDto> findAllContacts(Pageable pageable) {
        var contacts = contactRepository.findAll(pageable).map(contactMapper::contactToContactResponseDto);
        return new CustomPageDto<>(contacts.getContent(), pageable.getPageNumber(),
                pageable.getPageSize(), contacts.getTotalElements());
    }

    @Transactional(readOnly = true)
    public ContactResponseDto findContact(UUID contactUUID) {

        var contact = findContactByGuid(contactUUID);

        return contactMapper.contactToContactResponseDto(contact);
    }

    @Transactional(readOnly = true)
    public CustomPageDto<ContactResponseDto> searchContacts(String email, String name, Pageable pageable) {
        var user = findUserByEmail(email);
        var userContacts = contactRepository
                .findAllByUserAndFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(user, name, name, pageable)
                .map(contactMapper::contactToContactResponseDto);
        return new CustomPageDto<>(userContacts.getContent(), pageable.getPageNumber(),
        pageable.getPageSize(), userContacts.getTotalElements());
    }

    @Transactional(readOnly = true)
    public CustomPageDto<ContactResponseDto> findAllUserContacts(User user, Pageable pageable) {
        var userContacts = contactRepository.findAllByUser(user, pageable)
                .map(contactMapper::contactToContactResponseDto);
        return new CustomPageDto<>(userContacts.getContent(), pageable.getPageNumber(),
                pageable.getPageSize(), userContacts.getTotalElements());

    }

    @Transactional(rollbackFor = {RuntimeException.class})
    public ContactResponseDto updateContact(UUID contactUUID, UpdateContactRequestDto updateContactRequestDto, String userEmail) {

        var contact = findContactByGuid(contactUUID);

        var user = findUserByEmail(userEmail);

        if (!contact.getUser().equals(user)) {
            throw new ActionNotAllowedException(CommonErrorMessages.ACCESS_DENIED);
        }

        contactMapper.updateEntityFromRequest(contact, updateContactRequestDto);

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
