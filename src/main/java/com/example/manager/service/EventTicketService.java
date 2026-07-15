package com.example.manager.service;

import com.example.manager.dto.EventTicketDTO;
import com.example.manager.dto.creation.EventTicketCreationDTO;
import com.example.manager.mapper.EventTicketMapper;
import com.example.manager.model.Event;
import com.example.manager.model.EventTicket;
import com.example.manager.repository.EventTicketRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class EventTicketService {

    private final EventTicketRepository eventTicketRepository;
    private final EventService eventService;
    private final EventTicketMapper eventTicketMapper;

    @Transactional
    public EventTicketDTO createEventTicket(EventTicketCreationDTO eventTicketCreation) {

        EventTicket eventTicket = EventTicket.builder()
                .event(eventService.findEventById(eventTicketCreation.getEventId()))
                .price(eventTicketCreation.getPrice())
                .totalQuantity(eventTicketCreation.getTotalQuantity())
                .build();
        return eventTicketMapper.toDto(eventTicketRepository.save(eventTicket));

    }

    public EventTicket findEventTicketById (int id) {
        var eventTicketOptional = eventTicketRepository.findById(id);
        return  eventTicketOptional.orElseThrow(() -> new RuntimeException("Event ticket not found"));
    }

    @Transactional
    public EventTicketDTO updateReserved (EventTicketDTO eventTicketDTO,Integer number) {
        if (eventTicketDTO.getTotalQuantity() > (eventTicketDTO.getTotalQuantity() +
                number + eventTicketDTO.getSoldQuantity())) {
            eventTicketDTO.setReservedQuantity(eventTicketDTO.getReservedQuantity() + number);
            this.update(eventTicketMapper.toEntity(eventTicketDTO));
            return eventTicketDTO;
        }
        return eventTicketDTO;
    }

    @Transactional
    public EventTicketDTO updateSold (EventTicketDTO eventTicketDTO, Integer number) {
        if (eventTicketDTO.getTotalQuantity() > (eventTicketDTO.getTotalQuantity() +
                number + eventTicketDTO.getSoldQuantity())) {
            eventTicketDTO.setSoldQuantity(eventTicketDTO.getSoldQuantity() + number);
            this.update(eventTicketMapper.toEntity(eventTicketDTO));
            return eventTicketDTO;
        }
        return eventTicketDTO;
    }

    @Transactional
    public EventTicket update (EventTicket eventTicket) {
        return eventTicketRepository.save(merge(
                findEventTicketById(eventTicket.getId())
                ,eventTicket));
    }

    private EventTicket merge (EventTicket src, EventTicket dest) {
        if (src.getEvent().getId() != null) {
            dest.setEvent(src.getEvent());
        }
        if (src.getPrice() != null) {
            dest.setPrice(src.getPrice());
        }
        if (src.getTotalQuantity() != null) {
            dest.setTotalQuantity(src.getTotalQuantity());
        }
        if (src.getSoldQuantity() != null) {
            dest.setSoldQuantity(src.getSoldQuantity());
        }
        if (src.getReservedQuantity() != null) {
            dest.setReservedQuantity(src.getReservedQuantity());
        }
        return dest;
    }

}
