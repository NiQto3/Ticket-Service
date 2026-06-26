package com.example.manager.mapper;

import com.example.manager.dto.EventCreation;
import com.example.manager.dto.EventDTO;
import com.example.manager.model.Event;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EventMapper {

    @Mapping(target = "organizerId", source = "organizer.id")
    @Mapping(target = "locationId", source = "location.id")
    EventDTO toDto(Event event);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "organizer", ignore = true)
    @Mapping(target = "location", ignore = true)
    Event toEntity(EventDTO eventDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "organizer", ignore = true)
    @Mapping(target = "location", ignore = true)
    Event toEntity(EventCreation eventCreation);
}
