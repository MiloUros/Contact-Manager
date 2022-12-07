package com.ingsoftware.contactmanager.controllers;


import com.ingsoftware.contactmanager.domain.contactDtos.ContactResponseDto;
import com.ingsoftware.contactmanager.domain.contactDtos.ContactRequestDto;
import com.ingsoftware.contactmanager.services.ContactService;
import com.ingsoftware.contactmanager.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping()
public class ContactController {

    private final ContactService contactService;
    private final UserService userService;

    @GetMapping("/contacts/all")
    public ResponseEntity<List<ContactResponseDto>> findAllContacts() {
        return ResponseEntity.ok(contactService.findAllContacts());
    }

    @GetMapping("/{contactUUID}/contacts")
    public ResponseEntity<ContactResponseDto> findContact(@PathVariable("contactUUID") UUID contactId) {
        return ResponseEntity.ok(contactService.findContact(contactId));
    }

    @PostMapping("/contacts/all")
    public ResponseEntity<List<ContactResponseDto>> findAllContactsForUser(
            @CurrentSecurityContext(expression="authentication.name") String email) {
        return ResponseEntity.ok(contactService.findAllUserContacts(userService.findUser(email).getGuid()));
    }

    @PutMapping("/{contactUUID}/contacts")
    public ResponseEntity<ContactResponseDto> updateContact(@PathVariable("contactUUID") UUID contactId
            , @RequestBody @Valid ContactRequestDto contactRequestDto,
                                                @CurrentSecurityContext(expression="authentication.name") String email) {
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(contactService.editContact(contactId, contactRequestDto, email));
    }

    @DeleteMapping("/{contactUUID}/contacts")
    public ResponseEntity<String> deleteUserContactById(@CurrentSecurityContext(expression="authentication.name")
                                                            String email, @PathVariable("contactUUID") UUID contactId) {
        contactService.deleteContactById(email, contactId);
        return ResponseEntity.status(HttpStatus.OK).body(HttpStatus.OK.name());
    }

    @PostMapping("/contacts")
    public ResponseEntity<ContactResponseDto> createContact(@RequestBody @Valid ContactRequestDto contactRequestDto,
                                        @CurrentSecurityContext(expression="authentication.name") String email) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(contactService.addContact(contactRequestDto, userService.findUser(email).getGuid()));
    }

}
