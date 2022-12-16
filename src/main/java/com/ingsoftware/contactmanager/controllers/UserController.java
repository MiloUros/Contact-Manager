package com.ingsoftware.contactmanager.controllers;

import com.ingsoftware.contactmanager.domain.dtos.CustomPageDto;
import com.ingsoftware.contactmanager.domain.dtos.userDtos.UpdateUserRequestDto;
import com.ingsoftware.contactmanager.domain.dtos.userDtos.UserRequestDto;
import com.ingsoftware.contactmanager.domain.dtos.userDtos.UserResponseDto;
import com.ingsoftware.contactmanager.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/all")
    public ResponseEntity<CustomPageDto<UserResponseDto>> findAllUsers(Pageable pageable) {
        return ResponseEntity.ok(userService.findAllUsers(pageable));
    }

    @GetMapping("/{userUUID}")
    public ResponseEntity<UserResponseDto> findUser(@PathVariable UUID userUUID) {
        return ResponseEntity.ok(userService.findUser(userUUID));
    }

    @PostMapping()
    public ResponseEntity<UserResponseDto> createUser(@RequestBody @Valid UserRequestDto userRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(userRequestDto));
    }

    @DeleteMapping("/{userUUID}")
    public ResponseEntity<String> deleteUser(@PathVariable("userUUID") UUID userUUID) {
        userService.deleteUser(userUUID);
        return ResponseEntity.ok(HttpStatus.OK.name());
    }

    @PutMapping("/{userUUID}")
    public ResponseEntity<UserResponseDto> updateUser(@PathVariable("userUUID") UUID userID,
                                                      @RequestBody @Valid UpdateUserRequestDto updateUserRequestDto) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(userService.updateUser(userID, updateUserRequestDto));
    }

}
