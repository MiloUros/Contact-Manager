package com.ingsoftware.contactmanager.controllers;


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

    @PutMapping("/{contactId}")
    public ResponseEntity<String> updateContact(@PathVariable("contactId") UUID contactId, @RequestBody ContactRequestDto contactRequestDto) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(contactService.editContact(contactId, contactRequestDto));
    }
}
