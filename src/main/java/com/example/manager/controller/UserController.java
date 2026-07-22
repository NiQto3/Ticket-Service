package com.example.manager.controller;

import com.example.manager.dto.UserDTO;
import com.example.manager.mapper.UserMapper;
import com.example.manager.security.UserDetailsInfo;
import com.example.manager.service.ErrorHandler;
import com.example.manager.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "${cors.url}", maxAge = 3600, allowCredentials = "true")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class UserController {

    private final UserService userService;

    @GetMapping("/current")
    @ResponseStatus(HttpStatus.OK)
    public UserDTO getCurrentUser () {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!principal.getClass().equals(UserDetailsInfo.class))
            throw new RuntimeException("Not authorized");
        return userService.findById(((UserDetailsInfo) principal).getId());
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserDTO getUserById (@Valid @PathVariable Integer id) {
        return userService.findById(id);
    }

    @PatchMapping
    @ResponseStatus(HttpStatus.OK)
    public UserDTO updateUserRole (@Valid @RequestBody UserDTO userDTO, BindingResult bindingResult) {
        ErrorHandler.checkForErrors(bindingResult, "Update user failed");
        return userService.update(userDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUser (@Valid @PathVariable Integer id) {
        userService.delete(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<UserDTO> getUserList () {
        return userService.getUsers();
    }

}
