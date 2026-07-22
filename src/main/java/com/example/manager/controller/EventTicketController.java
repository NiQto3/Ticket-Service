package com.example.manager.controller;

import com.example.manager.dto.EventTicketDTO;
import com.example.manager.dto.creation.EventTicketCreationDTO;
import com.example.manager.service.ErrorHandler;
import com.example.manager.service.EventTicketService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/event_ticket")
@CrossOrigin(origins = "${cors.url}", maxAge = 3600, allowCredentials = "true")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class EventTicketController {

    private final EventTicketService eventTicketService;

    @PostMapping
    public EventTicketDTO createEventTicket (@Valid @RequestBody EventTicketCreationDTO eventTicketCreationDTO,
                                             BindingResult bindingResult) {
        ErrorHandler.checkForErrors(bindingResult, "Event ticket creation failed");
        return eventTicketService.createEventTicket(eventTicketCreationDTO);
    }

    @PatchMapping
    public EventTicketDTO updateEventTicket (@Valid @RequestBody EventTicketDTO eventTicketDTO,
                                             BindingResult bindingResult) {
        ErrorHandler.checkForErrors(bindingResult, "Event ticket update failed");
        return eventTicketService.update(eventTicketDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteEventTicket (@Valid @PathVariable Integer id) {
        eventTicketService.delete(id);
    }

    @GetMapping("/{id}")
    public EventTicketDTO getEventTicket (@Valid @PathVariable Integer id, BindingResult bindingResult) {
        ErrorHandler.checkForErrors(bindingResult, "Event ticket get failed");
        return eventTicketService.getById(id);
    }

    @GetMapping
    public List<EventTicketDTO> getAllEventTickets () {
        return eventTicketService.getAll();
    }
}