package com.ingsoftware.contactmanager.controllers;


import com.ingsoftware.contactmanager.domain.dtos.CustomPageDto;
import com.ingsoftware.contactmanager.domain.dtos.contactDtos.ContactRequestDto;
import com.ingsoftware.contactmanager.domain.dtos.contactDtos.ContactResponseDto;
import com.ingsoftware.contactmanager.domain.dtos.contactDtos.UpdateContactRequestDto;
import com.ingsoftware.contactmanager.services.ContactService;
import com.ingsoftware.contactmanager.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/contacts")
public class ContactController {

    private final ContactService contactService;
    private final UserService userService;

    @Operation(summary = "Get all contacts.")
    @ApiResponse(responseCode = "200", content = {
            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ContactResponseDto.class))
    })
    @GetMapping("/all")
    public ResponseEntity<CustomPageDto<ContactResponseDto>> findAllContacts(Pageable pageable) {
        return ResponseEntity.ok(contactService.findAllContacts(pageable));
    }

    @Operation(summary = "Get one contact.")
    @ApiResponse(responseCode = "200", content = {
            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ContactResponseDto.class))
    })
    @GetMapping("/{contactUUID}")
    public ResponseEntity<ContactResponseDto> findContact(@PathVariable("contactUUID") UUID contactId) {
        return ResponseEntity.ok(contactService.findContact(contactId));
    }

    @Operation(summary = "Get all contacts for users.")
    @ApiResponse(responseCode = "200", content = {
            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ContactResponseDto.class))
    })
    @GetMapping()
    public ResponseEntity<CustomPageDto<ContactResponseDto>> findAllContactsForUser(
            @CurrentSecurityContext(expression = "authentication.name") String email, Pageable pageable) {
        return ResponseEntity.ok(contactService.findAllUserContacts(contactService.findUserByEmail(email), pageable));
    }

    @Operation(summary = "Search contacts.")
    @ApiResponse(responseCode = "200", content = {
            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ContactResponseDto.class))
    })
    @GetMapping(value = "/search")
    public ResponseEntity<CustomPageDto<ContactResponseDto>> searchContacts(
            @CurrentSecurityContext(expression = "authentication.name") String email,
            @RequestParam("param") @NonNull String name,
            Pageable pageable) {
        return ResponseEntity.ok(contactService.searchContacts(email, name,  pageable));
    }

    @Operation(summary = "Update contacts.")
    @ApiResponse(responseCode = "202", content = {
            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ContactResponseDto.class))
    })
    @PutMapping("/{contactUUID}")
    public ResponseEntity<ContactResponseDto> updateContact(@PathVariable("contactUUID") UUID contactId
            , @RequestBody @Valid UpdateContactRequestDto updateContactRequestDto,
                                                            @CurrentSecurityContext(expression = "authentication.name") String email) {
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(contactService.updateContact(contactId, updateContactRequestDto, email));
    }

    @Operation(summary = "Delete user contacts.")
    @ApiResponse(responseCode = "200", content = {
            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ContactResponseDto.class))
    })
    @DeleteMapping("/{contactUUID}")
    public ResponseEntity<String> deleteUserContact(@CurrentSecurityContext(expression = "authentication.name")
                                                    String email, @PathVariable("contactUUID") UUID contactId) {
        contactService.deleteContact(email, contactId);
        return ResponseEntity.status(HttpStatus.OK).body(HttpStatus.OK.name());
    }

    @Operation(summary = "Create contact.")
    @ApiResponse(responseCode = "201", content = {
            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ContactResponseDto.class))
    })
    @PostMapping()
    public ResponseEntity<ContactResponseDto> createContact(@RequestBody @Valid ContactRequestDto contactRequestDto,
                                                            @CurrentSecurityContext(expression = "authentication.name") String email) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(contactService.createContact(contactRequestDto, userService.findUser(email)));
    }
}
