package com.ingsoftware.contactmanager.controllers;

import com.ingsoftware.contactmanager.domain.dtos.CustomPageDto;
import com.ingsoftware.contactmanager.domain.dtos.contacTypeDtos.ContactTypeRequestDto;
import com.ingsoftware.contactmanager.domain.dtos.contacTypeDtos.ContactTypeResponseDto;
import com.ingsoftware.contactmanager.services.ContactTypeService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Api(tags = "Contact Type Controller")
@RequestMapping("/contact-types")
public class ContactTypeController {

    private final ContactTypeService contactTypeService;

    @Operation(summary = "Get all contact_types.")
    @ApiResponse(responseCode = "200", content = {
            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ContactTypeResponseDto.class))
    })
    @GetMapping()
    public ResponseEntity<CustomPageDto<ContactTypeResponseDto>> findAll(Pageable pageable) {
        return ResponseEntity.ok(contactTypeService.findAll(pageable));
    }

    @Operation(summary = "Get one contact_type.")
    @ApiResponse(responseCode = "200", content = {
            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ContactTypeResponseDto.class))
    })
    @GetMapping(value = "/{contactTypeUUID}")
    public ResponseEntity<ContactTypeResponseDto> findContactType(@PathVariable UUID contactTypeUUID) {
        return ResponseEntity.ok(contactTypeService.findContactType(contactTypeUUID));
    }

    @Operation(summary = "Create contact_types.")
    @ApiResponse(responseCode = "200", content = {
            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ContactTypeResponseDto.class))
    })
    @PostMapping()
    public ResponseEntity<ContactTypeResponseDto> createContactType(@RequestBody @Valid ContactTypeRequestDto contactTypeRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).
                body(contactTypeService.createContactType(contactTypeRequestDto));
    }

    @Operation(summary = "Delete contact_type.")
    @ApiResponse(responseCode = "200", content = {
            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ContactTypeResponseDto.class))
    })
    @DeleteMapping(value = "/{contactTypeUUID}")
    public ResponseEntity<String> deleteContactType(@PathVariable UUID contactTypeUUID) {
        contactTypeService.deleteContactType(contactTypeUUID);
        return ResponseEntity.ok(HttpStatus.OK.name());
    }

    @Operation(summary = "Update contact_types.")
    @ApiResponse(responseCode = "202", content = {
            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ContactTypeResponseDto.class))
    })
    @PutMapping(value = "/{contactTypeUUID}")
    public ResponseEntity<ContactTypeResponseDto> updateContactType(@PathVariable UUID contactTypeUUID,
                                                                    @RequestBody @Valid ContactTypeRequestDto contactTypeRequestDto) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).
                body(contactTypeService.updateContactType(contactTypeUUID, contactTypeRequestDto));
    }
}
