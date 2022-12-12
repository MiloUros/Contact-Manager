package com.ingsoftware.contactmanager.services;

import com.ingsoftware.contactmanager.CommonErrorMessages;
import com.ingsoftware.contactmanager.domain.dtos.CustomPageDto;
import com.ingsoftware.contactmanager.domain.dtos.contacTypeDtos.ContactTypeRequestDto;
import com.ingsoftware.contactmanager.domain.dtos.contacTypeDtos.ContactTypeResponseDto;
import com.ingsoftware.contactmanager.domain.entitys.ContactType;
import com.ingsoftware.contactmanager.domain.mappers.ContactTypeMapper;
import com.ingsoftware.contactmanager.exceptions.ContactNotFoundException;
import com.ingsoftware.contactmanager.exceptions.ContactTypeExistsException;
import com.ingsoftware.contactmanager.exceptions.ContactTypeNotFound;
import com.ingsoftware.contactmanager.repositories.ContactTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public CustomPageDto<ContactTypeResponseDto> findAll(Pageable pageable) {
        var contactTypes = contactTypeRepository.findAll(pageable)
                .map(contactTypeMapper::entityToContactTypeResponseDto);
        return new CustomPageDto<>(contactTypes.getContent(), pageable.getPageNumber(),
                pageable.getPageSize(), contactTypes.getTotalElements());

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
