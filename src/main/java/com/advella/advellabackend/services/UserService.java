package com.advella.advellabackend.services;

import com.advella.advellabackend.model.User;
import com.advella.advellabackend.repositories.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService implements UserDetailsService {
    private final IUserRepository userRepository;

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public List<User> getFiveLatestUsers(int amount) {
        return userRepository.getFiveLatestUsers(amount);
    }

    public List<User> getUsersByLocation(String location) {
        return userRepository.getUsersByLocation(location);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }

    public int registeredUsers(Date fromDate, Date toDate) {
        return userRepository.registeredUsers(fromDate, toDate);
    }
}
