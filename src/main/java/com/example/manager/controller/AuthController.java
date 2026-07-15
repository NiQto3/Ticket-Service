package com.example.manager.controller;

import com.example.manager.dto.*;
import com.example.manager.dto.creation.UserCreationDTO;
import com.example.manager.model.User;
import com.example.manager.security.UserDetailsInfo;
import com.example.manager.security.service.AuthService;
import com.example.manager.security.util.JWTUtil;
import com.example.manager.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "${cors.url}", maxAge = 3600, allowCredentials = "true")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class AuthController {

    private final JWTUtil jwtUtil;
    private final AuthService authService;
    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public AuthResponse login (@RequestBody AuthRequest authRequest) {

        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword());
        authenticationManager.authenticate(token);

        User user = ((UserDetailsInfo) authService.loadUserByUsername(authRequest.getUsername())).user();
        String jwtToken = jwtUtil.generateToken(user);

        return new AuthResponse(
                jwtToken,
                new AuthoritiesDTO(
                                user.getRole(),
                                user.getId()
                        ),
                System.currentTimeMillis()
                );
    }

    @PostMapping("/registration")
    @ResponseStatus(HttpStatus.OK)
    public AuthResponse registration (@Valid @RequestBody UserCreationDTO userCreationDTO,
                                      BindingResult bindingResult) {
        ErrorHandler.checkForErrors(bindingResult, "Registration failed");

        User user = userService.createUser(userCreationDTO);
        String  jwtToken = jwtUtil.generateToken(user);

        return new AuthResponse(
                jwtToken,
                new AuthoritiesDTO(
                        user.getRole(),
                        user.getId()
                ),
                System.currentTimeMillis()
        );
    }

    @PutMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN') or #userId == authentication.principal.id")
    public void changePassword (@PathVariable Integer userId, @RequestBody PasswordChangeDTO passwordChangeDTO) {
        authService.ChangePassword(userService.findUserById(userId),
                                   passwordChangeDTO);
    }

}
