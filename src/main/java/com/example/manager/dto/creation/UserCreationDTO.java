package com.example.manager.dto.creation;

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
public class UserCreationDTO {
    @NotBlank
    @Size(max = 45)
    private String username;

    @NotBlank
    @Size(max = 255)
    private String password;

    @NotBlank
    @Email
    @Size(max = 255)
    private String email;
}
