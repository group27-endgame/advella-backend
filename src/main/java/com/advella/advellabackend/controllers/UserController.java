package com.advella.advellabackend.controllers;

import com.advella.advellabackend.model.User;
import com.advella.advellabackend.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
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
    public ResponseEntity<List<User>> getFiveLatestUsers(@RequestParam(required = false, defaultValue = "5") int amount) {
        return ResponseEntity.ok(userService.getFiveLatestUsers(amount));
    }

    @GetMapping("/users/{location}")
    public ResponseEntity<List<User>> getUsersByLocation(@PathVariable String location) {
        return ResponseEntity.ok(userService.getUsersByLocation(location));
    }

    @GetMapping("/users/registered/{fromDate}/{toDate}")
    public ResponseEntity<Integer> getRegisteredUsers(@PathVariable long fromDate, @PathVariable long toDate) {
        return ResponseEntity.ok(userService.registeredUsers(new Date(fromDate), new Date(toDate)));
    }
}
