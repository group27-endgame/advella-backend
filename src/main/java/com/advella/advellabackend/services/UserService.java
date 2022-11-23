package com.advella.advellabackend.services;

import com.advella.advellabackend.model.*;
import com.advella.advellabackend.model.compositeKeys.BidProductId;
import com.advella.advellabackend.model.compositeKeys.BidServiceId;
import com.advella.advellabackend.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService implements UserDetailsService {
    private final IProductRepository productRepository;
    private final IServiceRepository serviceRepository;
    private final IBidServiceRepository bidServiceRepository;
    private final IBidProductRepository bidProductRepository;
    private final IUserRepository userRepository;
    private final IRoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public List<User> getBasicUserInfos() {
        List<Object[]>objectRecords = userRepository.getUsersIdAndUsername();
        List<User> users = new ArrayList<>();
        for (Object[] object:objectRecords) {
            users.add(new User(Integer.valueOf((Integer) object[0]),String.valueOf(object[1])));
        }
        return users;
    }

    public void saveAllUsers(List<User> usersToSave) {
        userRepository.saveAll(usersToSave);
    }

    public List<User> getLatestUsers(int amount) {
        List<User> usersToReturn = userRepository.getLatestUsers(amount);
        return usersToReturn;
    }

    public ResponseEntity<Void> deleteUserById(Integer userId) {
        if (!doesUserExist(userId)) {
            return ResponseEntity.notFound().build();
        }
        User userToDelete = userRepository.findById(userId).orElseThrow();
        userToDelete.getPostedService().forEach(s -> s.setPosted(null));
        userToDelete.getPostedProduct().forEach(p -> p.setPosted(null));
        userToDelete.getBidServices().forEach(b -> bidServiceRepository.delete(b));
        userToDelete.getBidProducts().forEach(p -> bidProductRepository.delete(p));
        userToDelete.getRoles().forEach(r -> r.getUsers().remove(userToDelete));
        roleRepository.saveAll(userToDelete.getRoles());
        userRepository.delete(userToDelete);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<User> getUserById(Integer userId) {
        if (!doesUserExist(userId)) {
            return ResponseEntity.notFound().build();
        }
        User user = userRepository.getReferenceById(userId);

        return ResponseEntity.ok(user);
    }

    public ResponseEntity<List<User>> getUsersByLocation(String location) {
        if (userRepository.getUsersByLocation(location).isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        List<User> usersToReturn = userRepository.getUsersByLocation(location);

        return ResponseEntity.ok(usersToReturn);
    }

    public Integer registeredUsers(Date fromDate, Date toDate) {
        Integer returnValue = userRepository.registeredUsers(fromDate, toDate);
        if (returnValue == null) {
            return 0;
        }
        return returnValue;
    }

    public ResponseEntity<Void> bidOnProduct(int productId, String token, int amount) {
        Product productToBidTo;

        try {
            productToBidTo = productRepository.findById(productId).orElseThrow();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
        User user = getUserFromHeader(token);
        BidProduct bidProduct = bidProductRepository.getBidProduct(productId, user.getUserId());

        if (bidProduct == null) {
            bidProduct = new BidProduct();
        }

        bidProduct.setAmount(amount);
        bidProduct.setBiddedProduct(productToBidTo);
        bidProduct.setProductBidder(user);
        bidProductRepository.save(bidProduct);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<Void> bidOnService(int serviceId, String token, int amount) {
        com.advella.advellabackend.model.Service serviceToBidTo;
        try {
            serviceToBidTo = serviceRepository.findById(serviceId).orElseThrow();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
        User user = getUserFromHeader(token);
        BidService bidService = bidServiceRepository.getBidService(serviceId, user.getUserId());

        if (bidService == null) {
            bidService = new BidService();
        }

        bidService.setAmount(amount);
        bidService.setBiddedService(serviceToBidTo);
        bidService.setServiceBidder(user);
        bidServiceRepository.save(bidService);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<User> changeUserRole(Integer userId) {
        User user = userRepository.getReferenceById(userId);
        if (user != null) {
            List<Role> userRoles = user.getRoles();
            for (Role role : userRoles) {
                if (role.getName().equals("admin")) {
                    userRoles.remove(role);
                    userRepository.save(user);
                    return ResponseEntity.ok(user);
                }
            }

            userRoles.add(roleRepository.findByRoleName("admin"));
            userRepository.save(user);

            return ResponseEntity.ok(user);
        }
        return ResponseEntity.notFound().build();
    }

    public boolean doesUserExist(int userId) {
        try {
            userRepository.findById(userId).orElseThrow();
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public ResponseEntity<Void> registerUser(User userToRegister) {
        if (userRepository.findByUsername(userToRegister.getUsername()) == null) {
            userToRegister.setRoles(Collections.singletonList(roleRepository.findByRoleName("user")));
            userToRegister.setPassword(passwordEncoder.encode(userToRegister.getPassword()));
            userRepository.save(userToRegister);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found in the database");
        } else {
            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
            user.getRoles().forEach(role -> {
                authorities.add(new SimpleGrantedAuthority(role.getName()));
            });
            return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
        }
    }


    public User getUserFromHeader(String header) {
        String[] payload = header.split("\\.");
        String payloadDecode = new String(Base64.getUrlDecoder().decode(payload[1]));
        String[] userArray = payloadDecode.split("\"");
        String userName = userArray[3];
        User user = userRepository.findByUsername(userName);
        return user;
    }
}
