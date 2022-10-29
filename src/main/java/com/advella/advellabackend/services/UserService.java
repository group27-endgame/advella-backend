package com.advella.advellabackend.services;

import com.advella.advellabackend.model.Product;
import com.advella.advellabackend.model.Role;
import com.advella.advellabackend.model.User;
import com.advella.advellabackend.repositories.IRoleRepository;
import com.advella.advellabackend.repositories.IUserRepository;
import io.swagger.models.Response;
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
    private final ProductService productService;
    private final ServiceService serviceService;
    private final IUserRepository userRepository;
    private final IRoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public List<User> getFiveLatestUsers(int amount) {
        List<User> usersToReturn = userRepository.getFiveLatestUsers(amount);
        for (User userToReturn : usersToReturn) {
            for (Role userRole : userToReturn.getRoles()) {
                userRole.setUsers(null);
            }
        }
        return usersToReturn;
    }

    public List<User> getUsersByLocation(String location) {
        return userRepository.getUsersByLocation(location);
    }

    public Integer registeredUsers(Date fromDate, Date toDate) {
        Integer returnValue = userRepository.registeredUsers(fromDate, toDate);
        if (returnValue == null) {
            return 0;
        }
        return returnValue;
    }

    public void bidOnProduct(int productId, String token) {
        User user = getUserFromHeader(token);
        Product productToBidTo = productService.getProductById(productId);
        user.getProducts().add(productToBidTo);
        userRepository.save(user);
    }

    public void bidOnService(int serviceId, String token) {
        User user = getUserFromHeader(token);
        com.advella.advellabackend.model.Service serviceToBidTo = serviceService.getServiceByID(serviceId);
        user.getServices().add(serviceToBidTo);
        userRepository.save(user);
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
        user.setRoles(null);
        return user;
    }
}
