package com.example.manager.dto;

import com.example.manager.model.Role;
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
    private Integer id;

    @NotBlank
    @Size(max = 45)
    private String username;

    @NotNull
    private Role role;

    @NotBlank
    @Email
    @Size(max = 255)
    private String email;
}
