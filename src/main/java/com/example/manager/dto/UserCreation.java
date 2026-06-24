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
    @NotBlank
    @Size(max = 45)
    private String username;

    @NotBlank
    @Size(max = 255)
    private String passwordHash;

    @NotBlank
    @Email
    @Size(max = 255)
    private String email;
}
