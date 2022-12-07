package com.ingsoftware.contactmanager.controllers;

import com.ingsoftware.contactmanager.domain.contacTypeDtos.ContactTypeResponseDto;
import com.ingsoftware.contactmanager.domain.contacTypeDtos.ContactTypeRequestDto;
import com.ingsoftware.contactmanager.services.ContactTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping()
public class ContactTypeController {

    private final ContactTypeService contactTypeService;

    @GetMapping("/contact-types/all")
    public ResponseEntity<List<ContactTypeResponseDto>> findAll() {
        return ResponseEntity.ok(contactTypeService.findAll());
    }

    @GetMapping("/contact-types")
    public ResponseEntity<ContactTypeResponseDto> findContactType(UUID contactTypeUUID) {
        return ResponseEntity.ok(contactTypeService.findOne(contactTypeUUID));
    }

    @PostMapping("/contact-types")
    public ResponseEntity<ContactTypeResponseDto> createContactType(@RequestBody @Valid ContactTypeRequestDto contactTypeRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).
                body(contactTypeService.createContactType(contactTypeRequestDto));
    }

    @DeleteMapping({"/{contactTypeUUID}/contact-types"})
    public ResponseEntity<String> deleteContactType(@PathVariable UUID contactTypeUUID) {
        contactTypeService.deleteContactTypeById(contactTypeUUID);
        return ResponseEntity.ok(HttpStatus.OK.name());
    }

    @PutMapping("/{contactTypeUUID}/contact-types")
    public ResponseEntity<ContactTypeResponseDto> updateContactType(@PathVariable UUID contactTypeUUID,
                                                    @RequestBody @Valid ContactTypeRequestDto contactTypeRequestDto) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).
                body(contactTypeService.updateContactTypeForContact(contactTypeUUID, contactTypeRequestDto));
    }
}
