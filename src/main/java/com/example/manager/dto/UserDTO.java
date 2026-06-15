package com.example.manager.dto;

import com.example.manager.model.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    @NotNull(message = "Must not be empty")
    private Integer id;

    @NotBlank(message = "Username is required")
    @Size(max = 45, message = "Username must be less than 45 characters")
    private String username;

    @NotNull(message = "Role is required")
    private UserRole role;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    @Size(max = 45, message = "Email must be less than 45 characters")
    private String email;
}
