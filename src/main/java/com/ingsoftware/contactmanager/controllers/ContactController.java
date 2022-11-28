package com.ingsoftware.contactmanager.controllers;


import com.ingsoftware.contactmanager.domain.contactDtos.ContactInfoDto;
import com.ingsoftware.contactmanager.services.ContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/contacts")
public class ContactController {

    private final ContactService contactService;

    @GetMapping()
    public ResponseEntity<List<ContactInfoDto>> findAllContacts() {
        return ResponseEntity.ok(contactService.findAllContacts());
    }

}
