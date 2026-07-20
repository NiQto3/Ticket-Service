package com.example.manager.mapper;

import com.example.manager.dto.EventTicketDTO;
import com.example.manager.dto.creation.EventTicketCreationDTO;
import com.example.manager.model.EventTicket;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EventTicketMapper {

    @Mapping(target = "eventId", source = "event.id")
    EventTicketDTO toDto(EventTicket eventTicket);

    @Mapping(target = "id", ignore = true)
    EventTicket toEntity(EventTicketDTO dto);

    @Mapping(target = "id", ignore = true)
    EventTicket toEntity(EventTicketCreationDTO eventTicketCreation);
}
