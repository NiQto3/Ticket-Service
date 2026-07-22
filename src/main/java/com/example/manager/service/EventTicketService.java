package com.example.manager.service;

import com.example.manager.dto.EventTicketDTO;
import com.example.manager.dto.creation.EventTicketCreationDTO;
import com.example.manager.mapper.EventTicketMapper;
import com.example.manager.model.EventTicket;
import com.example.manager.repository.EventTicketRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    public EventTicketDTO getById(int id) {
        return eventTicketMapper.toDto(getEventTicketById(id));
    }

    public List<EventTicketDTO> getAll () {
        List<EventTicketDTO> result = new ArrayList<>();
        for (EventTicket eventTicket : eventTicketRepository.findAll()) {
            result.add(eventTicketMapper.toDto(eventTicket));
        }
        return result;
    }

    public EventTicket getEventTicketById(int id) {
        var eventTicketOptional = eventTicketRepository.findById(id);
        return  eventTicketOptional.orElseThrow(() -> new RuntimeException("Event ticket not found"));
    }

    @Transactional
    public EventTicketDTO updateReserved (EventTicketDTO eventTicketDTO,Integer number) {
        if (eventTicketDTO.getTotalQuantity() > (eventTicketDTO.getReservedQuantity() +
                number + eventTicketDTO.getSoldQuantity())) {
            eventTicketDTO.setReservedQuantity(eventTicketDTO.getReservedQuantity() + number);
            this.updateSecured(eventTicketDTO);
            return eventTicketDTO;
        }
        return eventTicketDTO;
    }

    @Transactional
    public EventTicketDTO updateSold (EventTicketDTO eventTicketDTO, Integer number) {
        if (eventTicketDTO.getTotalQuantity() > (eventTicketDTO.getReservedQuantity() +
                number + eventTicketDTO.getSoldQuantity())) {
            eventTicketDTO.setSoldQuantity(eventTicketDTO.getSoldQuantity() + number);
            this.updateSecured(eventTicketDTO);
            return eventTicketDTO;
        }
        return eventTicketDTO;
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('EVENT_MANAGER')")
    public EventTicketDTO update (EventTicketDTO eventTicketDTO){
        return updateSecured(eventTicketDTO);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('EVENT_MANAGER')")
    public void delete (int id) {
        eventTicketRepository.deleteById(id);
    }

    @Transactional
    private EventTicketDTO updateSecured (EventTicketDTO eventTicketDto) {
        EventTicket eventTicket = eventTicketMapper.toEntity(eventTicketDto);
        return eventTicketMapper.toDto(eventTicketRepository.save(merge(
                eventTicket,
                getEventTicketById(eventTicket.getId())
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
