package com.example.manager.service;

import com.example.manager.dto.creation.UserCreationDTO;
import com.example.manager.model.Role;
import com.example.manager.model.User;
import com.example.manager.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private String hashPassword(String password) {
        String passwordHash = passwordEncoder.encode(password);
        return passwordHash;
    }

    public User createUser(UserCreationDTO userCreation) {
        User user = User.builder()
                .username(userCreation.getUsername())
                .passwordHash(hashPassword(userCreation.getPassword()))
                .role(Role.customer)
                .email(userCreation.getEmail())
                .build();
        userRepository.save(user);
        return user;
    }

    public User findUserById(int id) {
        var userOptional = userRepository.findById(id);
        return userOptional.orElseThrow(() -> new RuntimeException("user is not found"));
    }
}
