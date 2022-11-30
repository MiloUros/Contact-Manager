package com.ingsoftware.contactmanager.controllers;


import com.ingsoftware.contactmanager.domain.contactDtos.ContactCreationDto;
import com.ingsoftware.contactmanager.domain.contactDtos.ContactInfoDto;
import com.ingsoftware.contactmanager.domain.contactDtos.ContactRequestDto;
import com.ingsoftware.contactmanager.services.ContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/contacts")
public class ContactController {

    private final ContactService contactService;

    @GetMapping()
    public ResponseEntity<List<ContactInfoDto>> findAllContacts() {
        return ResponseEntity.ok(contactService.findAllContacts());
    }

    @PostMapping("/all/{userId}")
    public ResponseEntity<List<ContactInfoDto>> findAllContacts(@PathVariable UUID userId) {
        return ResponseEntity.ok(contactService.findAllUserContacts(userId));
    }

    @PutMapping("/{contactId}")
    public ResponseEntity<String> updateContact(@PathVariable("contactId") UUID contactId, @RequestBody ContactRequestDto contactRequestDto) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(contactService.editContact(contactId, contactRequestDto));
    }

    @DeleteMapping("/{userId}/{contactId}")
    public ResponseEntity<String> deleteUserContactById(@PathVariable("userId") UUID userId, @PathVariable("contactId") UUID contactId) {
        return ResponseEntity.status(HttpStatus.OK).body(contactService.deleteContactById(userId, contactId));
    }

    @PostMapping("{id}")
    public ResponseEntity<String> createContact(@RequestBody ContactCreationDto contactCreationDto, @PathVariable("id") UUID userId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(contactService.addContact(contactCreationDto, userId));
    }

}
