package com.ingsoftware.contactmanager.controllers;

import com.ingsoftware.contactmanager.domain.contactDtos.ContactCreationDto;
import com.ingsoftware.contactmanager.domain.mappers.UserMapper;
import com.ingsoftware.contactmanager.domain.userDtos.UserInfoDto;
import com.ingsoftware.contactmanager.domain.userDtos.UserLogInDto;
import com.ingsoftware.contactmanager.domain.userDtos.UserRegisterDto;
import com.ingsoftware.contactmanager.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final UserMapper mapper;

    @GetMapping()
    public ResponseEntity<List<UserInfoDto>> findAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/hello")
    public ResponseEntity<String> helloWorldApi() {
        return ResponseEntity.ok(userService.helloWorldApi());
    }

    @PostMapping()
    public ResponseEntity<UserInfoDto> createUser(@RequestBody UserRegisterDto userRegisterDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.
                userToUserInfoDto(userService.registerUser(userRegisterDto)));
    }

    @PostMapping("/user")
    public ResponseEntity<UserInfoDto> userLogIn(@RequestBody UserLogInDto userLogInDto) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(mapper.userToUserInfoDto(userService.
                userLogIn(userLogInDto.getEmail(), userLogInDto.getPassword())));
    }

    @DeleteMapping("/{userId}/{contactId}")
    public ResponseEntity<String> deleteUserContactById(@PathVariable("userId") UUID userId, @PathVariable("contactId") UUID contactId) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.deleteContactById(userId, contactId));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable("id") UUID id) {
        return ResponseEntity.ok(userService.deleteUserById(id));
    }

    @PostMapping("/contacts/{id}")
    public ResponseEntity<String> createContact(@RequestBody ContactCreationDto contactCreationDto, @PathVariable("id") UUID id) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.addContact(contactCreationDto, id));
    }

}
