package com.example.manager.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserCreation {

    @NotBlank(message = "Username is required")
    @Size(max = 45, message = "Username must be less than 45 characters")
    private String username;

    @NotBlank(message = "Password is required")
    @Size(max = 45, message = "Password must be less than 45 characters")
    private String password;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    @Size(max = 45, message = "Email must be less than 45 characters")
    private String email;
}
