package com.example.manager.service;

import com.example.manager.dto.creation.EventTicketCreationDTO;
import com.example.manager.model.Event;
import com.example.manager.model.EventTicket;
import com.example.manager.repository.EventTicketRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor
public class EventTicketService {

    private final EventTicketRepository eventTicketRepository;
    private final EventService eventService;

    @Transactional
    public EventTicket createEventTicket(EventTicketCreationDTO eventTicketCreation) {

        EventTicket eventTicket = EventTicket.builder()
                .event(eventService.findEventById(eventTicketCreation.getEventId()))
                .price(eventTicketCreation.getPrice())
                .totalQuantity(eventTicketCreation.getTotalQuantity())
                .build();
        eventTicketRepository.save(eventTicket);

        return eventTicket;

    }

    public EventTicket findEventTicketById (int id) {
        var eventTicketOptional = eventTicketRepository.findById(id);
        return  eventTicketOptional.orElseThrow(() -> new RuntimeException("Event ticket not found"));
    }

}
