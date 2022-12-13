package com.ingsoftware.contactmanager.controllers;

import com.ingsoftware.contactmanager.domain.dtos.CustomPageDto;
import com.ingsoftware.contactmanager.domain.dtos.userDtos.UserRequestDto;
import com.ingsoftware.contactmanager.domain.dtos.userDtos.UserResponseDto;
import com.ingsoftware.contactmanager.services.UserService;
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
@Api(tags = "User Controller")
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Operation(summary = "Get all users.")
    @ApiResponse(responseCode = "200", content = {
            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = UserResponseDto.class))
    })
    @GetMapping()
    public ResponseEntity<CustomPageDto<UserResponseDto>> findAllUsers(Pageable pageable) {
        return ResponseEntity.ok(userService.findAllUsers(pageable));
    }

    @Operation(summary = "Get one users.")
    @ApiResponse(responseCode = "200", content = {
            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = UserResponseDto.class))
    })
    @GetMapping(value = "/{userUUID}")
    public ResponseEntity<UserResponseDto> findUser(@PathVariable UUID userUUID) {
        return ResponseEntity.ok(userService.findUser(userUUID));
    }

    @Operation(summary = "Create user.")
    @ApiResponse(responseCode = "201", content = {
            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = UserResponseDto.class))
    })
    @PostMapping()
    public ResponseEntity<UserResponseDto> createUser(@RequestBody @Valid UserRequestDto userRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(userRequestDto));
    }

    @Operation(summary = "Delete user.")
    @ApiResponse(responseCode = "200", content = {
            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = UserResponseDto.class))
    })
    @DeleteMapping(value = "/{userUUID}")
    public ResponseEntity<String> deleteUser(@PathVariable("userUUID") UUID userUUID) {
        userService.deleteUser(userUUID);
        return ResponseEntity.ok(HttpStatus.OK.name());
    }

    @Operation(summary = "Update user.")
    @ApiResponse(responseCode = "202", content = {
            @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = UserResponseDto.class))
    })
    @PutMapping(value = "/{userUUID}")
    public ResponseEntity<UserResponseDto> updateUser(@PathVariable("userUUID") UUID userID,
                                                      @RequestBody @Valid UserRequestDto userRequestDto) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(userService.updateUser(userID, userRequestDto));
    }

}
