package com.example.manager.controller;

import com.example.manager.dto.AuthRequest;
import com.example.manager.dto.AuthResponse;
import com.example.manager.dto.AuthoritiesDTO;
import com.example.manager.dto.creation.UserCreationDTO;
import com.example.manager.model.User;
import com.example.manager.security.UserDetailsInfo;
import com.example.manager.security.service.AuthService;
import com.example.manager.security.util.JWTUtil;
import com.example.manager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "${cors.url}", maxAge = 3600, allowCredentials = "true")
public class AuthController {

    private final JWTUtil jwtUtil;
    private final AuthService authService;
    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthController(JWTUtil jwtUtil ,AuthService authService,
                          AuthenticationManager authenticationManager, UserService userService) {
        this.jwtUtil = jwtUtil;
        this.authService = authService;
        this.userService = userService;
        this.authenticationManager = authenticationManager;
    }

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
    public AuthResponse registration (@RequestBody UserCreationDTO userCreationDTO,
                                      BindingResult bindingResult) {
        checkForErrors(bindingResult);

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

    private void checkForErrors(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMessage = new StringBuilder();
            for (ObjectError error : bindingResult.getAllErrors()) {
                errorMessage.append(error.getDefaultMessage()).append("; ");
            }
            throw new RuntimeException(errorMessage.toString());
        }
    }

}
