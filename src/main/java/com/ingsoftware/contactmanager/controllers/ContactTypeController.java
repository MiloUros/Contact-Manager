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
@RequestMapping("/contact-types")
public class ContactTypeController {

    private final ContactTypeService contactTypeService;

    @GetMapping("/all")
    public ResponseEntity<List<ContactTypeResponseDto>> findAll() {
        return ResponseEntity.ok(contactTypeService.findAll());
    }

    @GetMapping("/{contactTypeUUID}")
    public ResponseEntity<ContactTypeResponseDto> findContactType(@PathVariable UUID contactTypeUUID) {
        return ResponseEntity.ok(contactTypeService.findOne(contactTypeUUID));
    }

    @PostMapping()
    public ResponseEntity<ContactTypeResponseDto> createContactType(@RequestBody @Valid ContactTypeRequestDto contactTypeRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).
                body(contactTypeService.createContactType(contactTypeRequestDto));
    }

    @DeleteMapping({"/{contactTypeUUID}"})
    public ResponseEntity<String> deleteContactType(@PathVariable UUID contactTypeUUID) {
        contactTypeService.deleteContactTypeById(contactTypeUUID);
        return ResponseEntity.ok(HttpStatus.OK.name());
    }

    @PutMapping("/{contactTypeUUID}")
    public ResponseEntity<ContactTypeResponseDto> updateContactType(@PathVariable UUID contactTypeUUID,
                                                    @RequestBody @Valid ContactTypeRequestDto contactTypeRequestDto) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).
                body(contactTypeService.updateContactType(contactTypeUUID, contactTypeRequestDto));
    }
}
