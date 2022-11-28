package com.ingsoftware.contactmanager.services;

import com.ingsoftware.contactmanager.domain.contactDtos.ContactInfoDto;
import com.ingsoftware.contactmanager.domain.mappers.ContactMapper;
import com.ingsoftware.contactmanager.repositories.ContactRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
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
   
}
