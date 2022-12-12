package com.ingsoftware.contactmanager.services;

import com.ingsoftware.contactmanager.CommonErrorMessages;
import com.ingsoftware.contactmanager.domain.contacTypeDtos.ContactTypeRequestDto;
import com.ingsoftware.contactmanager.domain.contacTypeDtos.ContactTypeResponseDto;
import com.ingsoftware.contactmanager.domain.entitys.ContactType;
import com.ingsoftware.contactmanager.domain.mappers.ContactTypeMapper;
import com.ingsoftware.contactmanager.exceptions.ContactNotFoundException;
import com.ingsoftware.contactmanager.exceptions.ContactTypeExistsException;
import com.ingsoftware.contactmanager.exceptions.ContactTypeNotFound;
import com.ingsoftware.contactmanager.repositories.ContactTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class ContactTypeService {

    private final ContactTypeRepository contactTypeRepository;
    private final ContactTypeMapper contactTypeMapper;

    @Transactional(rollbackFor = {RuntimeException.class})
    public ContactTypeResponseDto createContactType(ContactTypeRequestDto contactTypeRequestDto) {

        if (contactTypeRepository.existsByValueIgnoreCase(contactTypeRequestDto.getValue())) {
            throw new ContactTypeExistsException(CommonErrorMessages.CONTACT_TYPE_TAKEN);
        }

        var contactType = contactTypeMapper.contactTypeRequestDtoToEntity(contactTypeRequestDto);
        contactTypeRepository.save(contactType);

        return contactTypeMapper.entityToContactTypeResponseDto(contactType);

    }

    @Transactional(rollbackFor = {RuntimeException.class})
    public void deleteContactType(UUID id) {

        if (!contactTypeRepository.existsByGuid(id)) {
            throw new ContactTypeNotFound(CommonErrorMessages.CONTACT_TYPE_NOT_FOUND);
        }

        contactTypeRepository.deleteByGuid(id);

    }

    @Transactional(readOnly = true)
    public List<ContactTypeResponseDto> findAll() {
        return contactTypeMapper.entityToContactTypeResponseDtoList(contactTypeRepository.findAll());

    }

    @Transactional(readOnly = true)
    public ContactTypeResponseDto findContactType(UUID contactTypeUUID) {
        var contactType = findContactTypeByGuid(contactTypeUUID);
        return contactTypeMapper.entityToContactTypeResponseDto(contactType);
    }

    @Transactional(rollbackFor = {RuntimeException.class})
    public ContactTypeResponseDto updateContactType(UUID contactTypeUUID, ContactTypeRequestDto contactTypeRequestDto) {

        var contactType = findContactTypeByGuid(contactTypeUUID);

        contactTypeMapper.updateEntityFromRequest(contactType, contactTypeRequestDto);
        return contactTypeMapper.entityToContactTypeResponseDto(contactType);
    }

    private ContactType findContactTypeByGuid(UUID contactTypeUUID) {
        return contactTypeRepository.findContactTypeByGuid(contactTypeUUID).orElseThrow(()
                -> new ContactNotFoundException(CommonErrorMessages.INVALID_CONTACT_TYPE_GUID));
    }
}
