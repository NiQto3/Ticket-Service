package com.example.manager.dto;

import com.example.manager.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthoritiesDTO {

    private Role role;
    private Integer userId;

}
