package com.ingsoftware.contactmanager.services;

import com.ingsoftware.contactmanager.domain.entity.User;
import com.ingsoftware.contactmanager.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public String helloWorldApi() {
        return "Hello World";
    }

    public User save(User user) {
      return   userRepository.save(user);
    }
}
