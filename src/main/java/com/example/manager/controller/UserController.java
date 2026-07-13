package com.example.manager.controller;

import com.example.manager.dto.UserDTO;
import com.example.manager.security.UserDetailsInfo;
import com.example.manager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "${cors.url}", maxAge = 3600, allowCredentials = "true")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/current")
    @ResponseStatus(HttpStatus.OK)
    public UserDTO getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!principal.getClass().equals(UserDetailsInfo.class))
            throw new RuntimeException("Not authorized");
        return userService.findById(((UserDetailsInfo) principal).getId());
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyRole('ADMIN')")
    public UserDTO getUserById(@PathVariable Integer id) {
        return userService.findById(id);
    }

}
