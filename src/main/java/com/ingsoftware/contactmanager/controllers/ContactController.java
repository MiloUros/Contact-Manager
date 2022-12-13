package com.ingsoftware.contactmanager.controllers;


import com.ingsoftware.contactmanager.domain.dtos.CustomPageDto;
import com.ingsoftware.contactmanager.domain.dtos.contactDtos.ContactRequestDto;
import com.ingsoftware.contactmanager.domain.dtos.contactDtos.ContactResponseDto;
import com.ingsoftware.contactmanager.domain.entitys.Contact;
import com.ingsoftware.contactmanager.services.ContactService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.SpecificationArgumentResolver;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Or;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Api(tags = "Contact Controller")
@RequestMapping("/contacts")
public class ContactController implements WebMvcConfigurer {

    private final ContactService contactService;

    @Operation(summary = "Get all contacts.")
    @ApiResponse(responseCode = "200", content = {
            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ContactResponseDto.class))
    })
    @GetMapping()
    public ResponseEntity<CustomPageDto<ContactResponseDto>> findAllContacts(Pageable pageable) {
        return ResponseEntity.ok(contactService.findAllContacts(pageable));
    }

    @Operation(summary = "Get one contact.")
    @ApiResponse(responseCode = "200", content = {
            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ContactResponseDto.class))
    })
    @GetMapping(value = "/{contactUUID}")
    public ResponseEntity<ContactResponseDto> findContact(@PathVariable("contactUUID") UUID contactId) {
        return ResponseEntity.ok(contactService.findContact(contactId));
    }

    @Operation(summary = "Get all contacts for a user.")
    @ApiResponse(responseCode = "200", content = {
            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ContactResponseDto.class))
    })
    @GetMapping(value = "/user")
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
            @Or({
                    @Spec(path = "firstName", spec = Like.class),
                    @Spec(path = "lastName", spec = Like.class),
                    @Spec(path = "email", spec = Like.class),
                    @Spec(path = "phoneNumber", spec = Like.class)
            }) Specification<Contact> spec, Pageable pageable) {
        return ResponseEntity.ok(contactService.searchContacts(spec, pageable));
    }

    @Operation(summary = "Update contact.")
    @ApiResponse(responseCode = "202", content = {
            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ContactResponseDto.class))
    })
    @PutMapping(value = "/{contactUUID}")
    public ResponseEntity<ContactResponseDto> updateContact(@PathVariable("contactUUID") UUID contactId
            , @RequestBody @Valid ContactRequestDto contactRequestDto,
                                                            @CurrentSecurityContext(expression = "authentication.name") String email) {
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(contactService.updateContact(contactId, contactRequestDto, email));
    }

    @Operation(summary = "Delete contact.")
    @ApiResponse(responseCode = "200", content = {
            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ContactResponseDto.class))
    })
    @DeleteMapping(value = "/{contactUUID}")
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
                .body(contactService.createContact(contactRequestDto, contactService.findUserByEmail(email)));
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new SpecificationArgumentResolver());
    }
}
