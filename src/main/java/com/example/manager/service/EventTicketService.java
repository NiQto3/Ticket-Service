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
    private final EventTicketMapper eventTicketMapper;

    @Transactional
    public EventTicketDTO createEventTicket(EventTicketCreationDTO eventTicketCreation) {

        EventTicket eventTicket = eventTicketMapper.toEntity(eventTicketCreation);
        return eventTicketMapper.toDto(eventTicketRepository.save(eventTicket));

    }

    public EventTicketDTO findById (int id) {
        return eventTicketMapper.toDto(findEventTicketById(id));
    }

    public EventTicket findEventTicketById (int id) {
        var eventTicketOptional = eventTicketRepository.findById(id);
        return  eventTicketOptional.orElseThrow(() -> new RuntimeException("Event ticket not found"));
    }

    @Transactional
    public EventTicketDTO updateReserved (EventTicketDTO eventTicketDTO,Integer number) {
        if (eventTicketDTO.getTotalQuantity() > (eventTicketDTO.getReservedQuantity() +
                number + eventTicketDTO.getSoldQuantity())) {
            eventTicketDTO.setReservedQuantity(eventTicketDTO.getReservedQuantity() + number);
            this.update(eventTicketDTO);
            return eventTicketDTO;
        }
        return eventTicketDTO;
    }

    @Transactional
    public EventTicketDTO updateSold (EventTicketDTO eventTicketDTO, Integer number) {
        if (eventTicketDTO.getTotalQuantity() > (eventTicketDTO.getReservedQuantity() +
                number + eventTicketDTO.getSoldQuantity())) {
            eventTicketDTO.setSoldQuantity(eventTicketDTO.getSoldQuantity() + number);
            this.update(eventTicketDTO);
            return eventTicketDTO;
        }
        return eventTicketDTO;
    }

    @Transactional
    public EventTicketDTO update (EventTicketDTO eventTicketDto) {
        EventTicket eventTicket = findEventTicketById(eventTicketDto.getId());
        return eventTicketMapper.toDto(eventTicketRepository.save(merge(
                eventTicket,
                findEventTicketById(eventTicket.getId())
        )));
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
