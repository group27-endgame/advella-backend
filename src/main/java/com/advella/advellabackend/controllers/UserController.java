package com.advella.advellabackend.controllers;

import com.advella.advellabackend.model.User;
import com.advella.advellabackend.services.UserService;
import lombok.RequiredArgsConstructor;
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

    @GetMapping("/users/currentUser")
    public ResponseEntity<User> getCurrentUser(@RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(userService.getUserFromHeader(token));
    }

    @PostMapping("/users/register")
    public ResponseEntity<Void> registerUser(@RequestBody User userToRegister) {
        return userService.registerUser(userToRegister);
    }

    @PostMapping("/users/bid/product")
    public ResponseEntity<Void> bidOnProduct(@RequestParam int productID, @RequestHeader("Authorization") String token) {
        userService.bidOnProduct(productID, token);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/users/bid/service")
    public ResponseEntity<Void> bidOnService(@RequestParam int serviceId, @RequestHeader("Authorization") String token) {
        userService.bidOnService(serviceId, token);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/users/dash-board/latest")
    public ResponseEntity<List<User>> getFiveLatestUsers(@RequestParam(required = false, defaultValue = "5") int amount) {
        return ResponseEntity.ok(userService.getFiveLatestUsers(amount));
    }

    @GetMapping("/users/dash-board/{location}")
    public ResponseEntity<List<User>> getUsersByLocation(@PathVariable String location) {
        return ResponseEntity.ok(userService.getUsersByLocation(location));
    }

    @GetMapping("/users/dash-board/registered/{fromDate}/{toDate}")
    public ResponseEntity<Integer> getRegisteredUsers(@PathVariable long fromDate, @PathVariable long toDate) {
        return ResponseEntity.ok(userService.registeredUsers(new Date(fromDate), new Date(toDate)));
    }
}
