package com.example.manager.service;

import com.example.manager.dto.UserDTO;
import com.example.manager.dto.creation.UserCreationDTO;
import com.example.manager.mapper.UserMapper;
import com.example.manager.model.Role;
import com.example.manager.model.User;
import com.example.manager.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    private String hashPassword(String password) {
        String passwordHash = passwordEncoder.encode(password);
        return passwordHash;
    }

    @Transactional
    public User createUser(UserCreationDTO userCreation) {

        if (userRepository.existsByEmail(userCreation.getEmail())) {
            throw new RuntimeException("Email already taken");
        }

        if (userRepository.existsByUsername(userCreation.getUsername())) {
            throw new RuntimeException("Username already taken");
        }

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
        return userOptional.orElseThrow(() -> new RuntimeException("User is not found"));
    }

    public UserDTO findById(int id){
        return userMapper.toDto(findUserById(id));
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(int id) {
        userRepository.deleteById(id);
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public User update(User user) {
        return userRepository.save(merge(
                findUserById(user.getId()),
                user
        ));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<User> getUsers() {
        return userRepository.findAllByOrderByIdAsc();
    }

    private User merge (User src, User dest) {
        if (src.getEmail() != null) {
            dest.setEmail(src.getEmail());
        }
        if (src.getRole() != null) {
            dest.setRole(src.getRole());
        }
        if (src.getUsername() != null) {
            dest.setUsername(src.getUsername());
        }
        return dest;
    }
}
