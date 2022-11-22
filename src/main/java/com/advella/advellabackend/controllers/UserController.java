package com.advella.advellabackend.controllers;

import com.advella.advellabackend.model.Product;
import com.advella.advellabackend.model.Service;
import com.advella.advellabackend.model.User;
import com.advella.advellabackend.services.UserService;
import io.swagger.annotations.ApiOperation;
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

    @ApiOperation(value = "Get users", notes = "Gets all users")
    @GetMapping("/users/dash-board")
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @ApiOperation(value = "Get current user", notes = "Returns current user by token")
    @GetMapping("/users/currentUser")
    public ResponseEntity<User> getCurrentUser(@RequestHeader("Authorization") String token) {
        User userToReturn = userService.getUserFromHeader(token);
        if (userToReturn == null) {
            return ResponseEntity.notFound().build();
        }

        for (Product product : userToReturn.getPostedProduct()) {
            product.setPosted(null);
        }
        for (Service service : userToReturn.getPostedService()) {
            service.setPosted(null);
        }

        return ResponseEntity.ok(userToReturn);
    }

    @ApiOperation(value = "Register user", notes = "Registers user")
    @PostMapping("/users/register")
    public ResponseEntity<Void> registerUser(@RequestBody User userToRegister) {
        return userService.registerUser(userToRegister);
    }

    @ApiOperation(value = "Bid on product", notes = "Bid on product by productId and token")
    @PostMapping("/users/bid/product/{productID}")
    public ResponseEntity<Void> bidOnProduct(@PathVariable int productID, @RequestHeader("Authorization") String token, @RequestParam int amount) {
        return userService.bidOnProduct(productID, token, amount);
    }

    @ApiOperation(value = "Bid on service", notes = "Bid on service by serviceId and token")
    @PostMapping("/users/bid/service/{serviceId}")
    public ResponseEntity<Void> bidOnService(@PathVariable int serviceId, @RequestHeader("Authorization") String token, @RequestParam int amount) {
        return userService.bidOnService(serviceId, token, amount);
    }

    @ApiOperation(value = "Get user by Id", notes = "Gets user by userId")
    @GetMapping("/users/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable int userId) {
        return userService.getUserById(userId);
    }

    @ApiOperation(value = "Get latest users", notes = "Gets requested amount of latest users")
    @GetMapping("/users/dash-board/latest")
    public ResponseEntity<List<User>> getLatestUsers(@RequestParam(required = false, defaultValue = "5") int amount) {
        return ResponseEntity.ok(userService.getLatestUsers(amount));
    }

    @ApiOperation(value = "Get users by location", notes = "Gets users by location")
    @GetMapping("/users/dash-board/location/{location}")
    public ResponseEntity<List<User>> getUsersByLocation(@PathVariable String location) {
        return userService.getUsersByLocation(location);
    }

    @ApiOperation(value = "Get registered users", notes = "Gets users registered from startDate to endDate")
    @GetMapping("/users/dash-board/registered/{startDate}/{endDate}")
    public ResponseEntity<Integer> getRegisteredUsers(@PathVariable long startDate, @PathVariable long endDate) {
        return ResponseEntity.ok(userService.registeredUsers(new Date(startDate), new Date(endDate)));
    }

    @ApiOperation(value = "Delete user", notes = "Deletes user by userId")
    @DeleteMapping("/users/dash-board/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer userId) {
        return userService.deleteUserById(userId);
    }

    @ApiOperation(value = "Change user role", notes = "Changes user role if the user was admin it is no longer, if the user is not admin then he will be set as one")
    @PutMapping("/users/dash-board")
    public ResponseEntity<User> changeUserRole(@RequestParam Integer userId) {
        return userService.changeUserRole(userId);
    }
}
