package com.ingsoftware.contactmanager.controllers;


import com.ingsoftware.contactmanager.domain.dtos.CustomPageDto;
import com.ingsoftware.contactmanager.domain.dtos.contactDtos.ContactRequestDto;
import com.ingsoftware.contactmanager.domain.dtos.contactDtos.ContactResponseDto;
import com.ingsoftware.contactmanager.domain.entitys.Contact;
import com.ingsoftware.contactmanager.services.ContactService;
import com.ingsoftware.contactmanager.services.UserService;
import lombok.RequiredArgsConstructor;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.web.SpecificationArgumentResolver;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Or;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
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
@RequestMapping("/contacts")
public class ContactController implements WebMvcConfigurer {

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
            @Or({
                    @Spec(path = "firstName", spec = Equal.class),
                    @Spec(path = "lastName", spec = Equal.class),
                    @Spec(path = "email", spec = Equal.class),
                    @Spec(path = "phoneNumber", spec = Equal.class)
            }) Specification<Contact> spec, Pageable pageable) {
        return ResponseEntity.ok(contactService.searchContacts(spec, pageable));
    }

    @PutMapping("/{contactUUID}")
    public ResponseEntity<ContactResponseDto> updateContact(@PathVariable("contactUUID") UUID contactId
            , @RequestBody @Valid ContactRequestDto contactRequestDto,
                                                            @CurrentSecurityContext(expression = "authentication.name") String email) {
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(contactService.updateContact(contactId, contactRequestDto, email));
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
                .body(contactService.createContact(contactRequestDto, userService.findUser(email).getGuid()));
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new SpecificationArgumentResolver());
    }
}
