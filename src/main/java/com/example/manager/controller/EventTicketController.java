package com.example.manager.controller;

import com.example.manager.dto.EventTicketDTO;
import com.example.manager.dto.creation.EventTicketCreationDTO;
import com.example.manager.service.EventTicketService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/event_ticket")
@CrossOrigin(origins = "${cors.url}", maxAge = 3600, allowCredentials = "true")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class EventTicketController {

    private final EventTicketService eventTicketService;

    @PutMapping("/")
    public EventTicketDTO createEventTicket (@Valid @RequestBody EventTicketCreationDTO eventTicketCreationDTO, BindingResult bindingResult) {
        ErrorHandler.checkForErrors(bindingResult, "Event ticket creation failed");
        return eventTicketService.createEventTicket(eventTicketCreationDTO);
    }

}
