package com.ingsoftware.contactmanager.services;

import com.ingsoftware.contactmanager.domain.contactDtos.ContactInfoDto;
import com.ingsoftware.contactmanager.domain.contactDtos.ContactRequestDto;
import com.ingsoftware.contactmanager.domain.mappers.ContactMapper;
import com.ingsoftware.contactmanager.exceptions.ContactNotFoundException;
import com.ingsoftware.contactmanager.exceptions.InvalidEmailException;
import com.ingsoftware.contactmanager.repositories.ContactRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class ContactService {

    private final ContactRepository contactRepository;
    private final ContactMapper contactMapper;

    public List<ContactInfoDto> findAllContacts() {
        List<ContactInfoDto> contactInfoDtoList = new ArrayList<>();

        for (var contacts : contactRepository.findAll()) {
            contactInfoDtoList.add(contactMapper.contactToContactInfoDto(contacts));
        }
        return contactInfoDtoList;
    }

    public String editContact(UUID contactId, ContactRequestDto contactRequestDto) {
        var contact = contactRepository.findByIdentifier(contactId).orElseThrow(()
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

        return "Updated";
    }
}
