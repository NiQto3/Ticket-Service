package com.example.manager.mapper;

import com.example.manager.dto.UserCreation;
import com.example.manager.dto.UserDTO;
import com.example.manager.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface UserMapper {

    @Mapping(target = "passwordHash", source = "passwordHash", ignore = true)
    UserDTO toDto(User user);

    @Mapping(target = "id", ignore = true)
    User toEntity(UserDTO userDto);

    @Mapping(target = "id", ignore = true)
    User toEntity(UserCreation userCreation);
}
