package com.example.manager.mapper;

import com.example.manager.dto.LocationCreation;
import com.example.manager.dto.LocationDTO;
import com.example.manager.model.Location;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LocationMapper {

    LocationDTO toDto(Location location);

    @Mapping(target = "id", ignore = true)
    Location toEntity(LocationDTO locationDto);

    @Mapping(target = "id", ignore = true)
    Location toEntity(LocationCreation locationCreation);
}