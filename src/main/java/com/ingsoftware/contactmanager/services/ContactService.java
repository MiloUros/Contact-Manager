package com.ingsoftware.contactmanager.services;

import com.ingsoftware.contactmanager.domain.entity.Contact;
import com.ingsoftware.contactmanager.repositories.ContactRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ContactService {

   private final ContactRepository contactRepository;

   public Contact save(Contact contact) {
       return contactRepository.save(contact);
   }
}
