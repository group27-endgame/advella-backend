package com.advella.advellabackend.controllers;

import com.advella.advellabackend.model.User;
import com.advella.advellabackend.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {
    private final UserService userService;

    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok(userService.getUsers());
    }

    @GetMapping("/users/latest")
    public ResponseEntity<List<User>> getFiveLatestUsers() {
        return ResponseEntity.ok(userService.getFiveLatestUsers());
    }

    @GetMapping("/users/{location}")
    public ResponseEntity<List<User>> getUsersByLocation(@PathVariable String location) {
        return ResponseEntity.ok(userService.getUsersByLocation(location));
    }
}
