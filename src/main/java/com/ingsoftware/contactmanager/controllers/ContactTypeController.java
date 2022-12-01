package com.ingsoftware.contactmanager.controllers;

import com.ingsoftware.contactmanager.domain.contacTypeDtos.ContactTypeInfoDto;
import com.ingsoftware.contactmanager.domain.contacTypeDtos.ContactTypeRequestDto;
import com.ingsoftware.contactmanager.services.ContactTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/contact_type")
public class ContactTypeController {

    private final ContactTypeService contactTypeService;

    @GetMapping
    public ResponseEntity<List<ContactTypeInfoDto>> findAll() {
        return ResponseEntity.ok(contactTypeService.findAll());
    }

    @PostMapping()
    public ResponseEntity<ContactTypeInfoDto> createContactType(@RequestBody ContactTypeRequestDto contactTypeRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).
                body(contactTypeService.createContactType(contactTypeRequestDto));
    }

    @DeleteMapping({"{id}"})
    public ResponseEntity<String> deleteContactType(@PathVariable UUID id) {
        return ResponseEntity.ok(contactTypeService.deleteContactTypeById(id));
    }

    @PutMapping("{contactId}")
    public ResponseEntity<String> updateContactType(@PathVariable UUID contactId,
                                                    @RequestBody ContactTypeRequestDto contactTypeRequestDto) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).
                body(contactTypeService.updateContactType(contactId, contactTypeRequestDto));
    }
}
