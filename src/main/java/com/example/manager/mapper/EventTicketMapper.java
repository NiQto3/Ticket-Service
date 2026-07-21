package com.example.manager.mapper;

import com.example.manager.dto.EventTicketDTO;
import com.example.manager.dto.creation.EventTicketCreationDTO;
import com.example.manager.model.EventTicket;
import com.example.manager.service.EventService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class EventTicketMapper {

    @Autowired
    protected EventService eventService;

    @Mapping(target = "eventId", source = "event.id")
    public abstract EventTicketDTO toDto(EventTicket eventTicket);

    @Mapping(target = "event", expression =
            "java(eventService.findEventById(dto.getEventId()))")
    public abstract EventTicket toEntity(EventTicketDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "soldQuantity", ignore  = true)
    @Mapping(target = "reservedQuantity", ignore  = true)
    @Mapping(target = "event", expression =
            "java(eventService.findEventById(eventTicketCreation.getEventId()))")
    public abstract EventTicket toEntity(EventTicketCreationDTO eventTicketCreation);
}
