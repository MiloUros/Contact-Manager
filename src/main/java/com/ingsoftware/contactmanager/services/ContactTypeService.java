package com.ingsoftware.contactmanager.services;

import com.ingsoftware.contactmanager.domain.contacTypeDtos.ContactTypeInfoDto;
import com.ingsoftware.contactmanager.domain.contacTypeDtos.ContactTypeRequestDto;
import com.ingsoftware.contactmanager.domain.entitys.Contact;
import com.ingsoftware.contactmanager.domain.mappers.ContactTypeMapper;
import com.ingsoftware.contactmanager.exceptions.ContactNotFoundException;
import com.ingsoftware.contactmanager.exceptions.ContactTypeExistsException;
import com.ingsoftware.contactmanager.exceptions.ContactTypeNotFound;
import com.ingsoftware.contactmanager.repositories.ContactRepository;
import com.ingsoftware.contactmanager.repositories.ContactTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class ContactTypeService {

    private final ContactTypeRepository contactTypeRepository;
    private final ContactTypeMapper contactTypeMapper;
    private final ContactRepository contactRepository;

    public ContactTypeInfoDto createContactType(ContactTypeRequestDto contactTypeRequestDto) {

        if (contactTypeRepository.existsByValueIgnoreCase(contactTypeRequestDto.getValue())) {
            throw new ContactTypeExistsException("Contact Type already taken!");
        }

        var contactType = contactTypeMapper.contactTypeDtoToEntity(contactTypeRequestDto);
        contactTypeRepository.save(contactType);

        return contactTypeMapper.entityToInfoDto(contactType);

    }

    public String deleteContactTypeById(UUID id) {

        if (!contactTypeRepository.existsByGuid(id)) {
            throw new ContactTypeNotFound("Contact Type not found");
        }

        var contactType = contactTypeRepository.findByGuid(id).orElseThrow(()
                -> new ContactTypeNotFound("Contact Type not found"));

        List<Contact> contactsWithContactType = contactType.getContactsPerContactType();

        if (contactsWithContactType != null) {
            throw new ContactTypeExistsException("Some contacts have this contact type and u can't delete it.");
        }

        contactTypeRepository.deleteByGuid(id);
        return "Deleted";

    }

    public List<ContactTypeInfoDto> findAll() {

        List<ContactTypeInfoDto> allContactTypes = new ArrayList<>();

        for (var contactType : contactTypeRepository.findAll()) {
            allContactTypes.add(contactTypeMapper.entityToInfoDto(contactType));
        }
        return allContactTypes;

    }

    public String updateContactType(UUID contactID, ContactTypeRequestDto contactTypeRequestDto) {

        var contact = contactRepository.findByGuid(contactID).orElseThrow(()
                -> new ContactNotFoundException("Invalid contact id"));

        var contactType = contactTypeRepository.findByValue(contactTypeRequestDto.getValue())
                .orElseThrow(() -> new ContactTypeExistsException("Contact Type not found"));

        contact.setContactType(contactType);
        contact.setType(contactType.getValue());

        return "Updated";
    }
}
