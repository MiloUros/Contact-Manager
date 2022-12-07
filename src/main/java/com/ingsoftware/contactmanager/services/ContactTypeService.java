package com.ingsoftware.contactmanager.services;

import com.ingsoftware.contactmanager.CommonErrorMessages;
import com.ingsoftware.contactmanager.domain.contacTypeDtos.ContactTypeRequestDto;
import com.ingsoftware.contactmanager.domain.contacTypeDtos.ContactTypeResponseDto;
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

    public ContactTypeResponseDto createContactType(ContactTypeRequestDto contactTypeRequestDto) {

        if (contactTypeRepository.existsByValueIgnoreCase(contactTypeRequestDto.getValue())) {
            throw new ContactTypeExistsException(CommonErrorMessages.CONTACT_TYPE_TAKEN);
        }

        var contactType = contactTypeMapper.contactTypeDtoToEntity(contactTypeRequestDto);
        contactTypeRepository.save(contactType);

        return contactTypeMapper.entityToInfoDto(contactType);

    }

    public void deleteContactTypeById(UUID id) {

        if (!contactTypeRepository.existsByGuid(id)) {
            throw new ContactTypeNotFound(CommonErrorMessages.CONTACT_TYPE_NOT_FOUND);
        }

        contactTypeRepository.deleteByGuid(id);

    }

    public List<ContactTypeResponseDto> findAll() {

        List<ContactTypeResponseDto> allContactTypes = new ArrayList<>();

        for (var contactType : contactTypeRepository.findAll()) {
            allContactTypes.add(contactTypeMapper.entityToInfoDto(contactType));
        }
        return allContactTypes;

    }

    public ContactTypeResponseDto findOne(UUID contactTypeUUID) {
        var contactType = contactTypeRepository.findByGuid(contactTypeUUID).orElseThrow(()
                -> new ContactNotFoundException(CommonErrorMessages.INVALID_CONTACT_TYPE_GUID));
        return contactTypeMapper.entityToInfoDto(contactType);
    }

    public ContactTypeResponseDto updateContactTypeForContact(UUID contactUUID, ContactTypeRequestDto contactTypeRequestDto) {

        var contact = contactRepository.findByGuid(contactUUID).orElseThrow(()
                -> new ContactNotFoundException(CommonErrorMessages.INVALID_CONTACT_GUID));

        var contactType = contactTypeRepository.findByValue(contactTypeRequestDto.getValue())
                .orElseThrow(() -> new ContactTypeExistsException(CommonErrorMessages.CONTACT_TYPE_NOT_FOUND));

        contact.setContactType(contactType);
        contact.setType(contactType.getValue());
        return contactTypeMapper.entityToInfoDto(contactType);
    }
}
