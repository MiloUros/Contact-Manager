package com.ingsoftware.contactmanager.controllers;

import com.ingsoftware.contactmanager.domain.entity.User;
import com.ingsoftware.contactmanager.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @GetMapping()
    public ResponseEntity<List<User>> findAllUsers() {
        return ResponseEntity.ok(userService.findAllUsers());
    }

    @GetMapping("/hello")
    public ResponseEntity<String> helloWorldApi() {
        return ResponseEntity.ok(userService.helloWorldApi());
    }
}
