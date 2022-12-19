package com.ingsoftware.contactmanager.controllers;


import com.ingsoftware.contactmanager.domain.dtos.CustomPageDto;
import com.ingsoftware.contactmanager.domain.dtos.contactDtos.ContactRequestDto;
import com.ingsoftware.contactmanager.domain.dtos.contactDtos.ContactResponseDto;
import com.ingsoftware.contactmanager.domain.dtos.contactDtos.UpdateContactRequestDto;
import com.ingsoftware.contactmanager.services.ContactService;
import com.ingsoftware.contactmanager.services.UserService;
import io.swagger.annotations.Api;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Api(tags = "Contact Controller")
@RequestMapping("/contacts")
public class ContactController {

    private final ContactService contactService;
    private final UserService userService;

    @GetMapping("/all")
    public ResponseEntity<CustomPageDto<ContactResponseDto>> findAllContacts(Pageable pageable) {
        return ResponseEntity.ok(contactService.findAllContacts(pageable));
    }

    @GetMapping("/{contactUUID}")
    public ResponseEntity<ContactResponseDto> findContact(@PathVariable("contactUUID") UUID contactId) {
        return ResponseEntity.ok(contactService.findContact(contactId));
    }

    @GetMapping()
    public ResponseEntity<CustomPageDto<ContactResponseDto>> findAllContactsForUser(
            @CurrentSecurityContext(expression = "authentication.name") String email, Pageable pageable) {
        return ResponseEntity.ok(contactService.findAllUserContacts(contactService.findUserByEmail(email), pageable));
    }

    @GetMapping(value = "/search")
    public ResponseEntity<CustomPageDto<ContactResponseDto>> searchContacts(
            @CurrentSecurityContext(expression = "authentication.name") String email,
            @RequestParam("param") @NonNull String name,
            Pageable pageable) {
        return ResponseEntity.ok(contactService.searchContacts(email, name,  pageable));
    }

    @PutMapping("/{contactUUID}")
    public ResponseEntity<ContactResponseDto> updateContact(@PathVariable("contactUUID") UUID contactId
            , @RequestBody @Valid UpdateContactRequestDto updateContactRequestDto,
                                                            @CurrentSecurityContext(expression = "authentication.name") String email) {
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(contactService.updateContact(contactId, updateContactRequestDto, email));
    }

    @DeleteMapping("/{contactUUID}")
    public ResponseEntity<String> deleteUserContact(@CurrentSecurityContext(expression = "authentication.name")
                                                    String email, @PathVariable("contactUUID") UUID contactId) {
        contactService.deleteContact(email, contactId);
        return ResponseEntity.status(HttpStatus.OK).body(HttpStatus.OK.name());
    }

    @PostMapping()
    public ResponseEntity<ContactResponseDto> createContact(@RequestBody @Valid ContactRequestDto contactRequestDto,
                                                            @CurrentSecurityContext(expression = "authentication.name") String email) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(contactService.createContact(contactRequestDto, userService.findUser(email)));
    }
}
