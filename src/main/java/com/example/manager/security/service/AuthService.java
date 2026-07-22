package com.example.manager.security.service;

import com.example.manager.dto.PasswordChangeDTO;
import com.example.manager.model.User;
import com.example.manager.repository.UserRepository;
import com.example.manager.security.UserDetailsInfo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class AuthService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("No user with such a name");
        }
        return new UserDetailsInfo(user.get());
    }

    public UserDetails loadUserByEmail(String email) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("No user with such email");
        }
        return new UserDetailsInfo(user.get());
    }

    @Transactional
    public boolean changePassword(User user, PasswordChangeDTO passwordChangeDTO) {
        if (this.passwordEncoder.matches(
                passwordChangeDTO.getOldPassword(),
                user.getPasswordHash())
           ) {
            user.setPasswordHash(passwordEncoder.encode(
                                 passwordChangeDTO.getNewPassword()));
            userRepository.save(user);
            return true;
        } else {
            return false;
        }
    }


}
