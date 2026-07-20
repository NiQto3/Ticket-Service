package com.example.manager.controller;

import com.example.manager.dto.EventDTO;
import com.example.manager.dto.creation.EventCreationDTO;
import com.example.manager.service.ErrorHandler;
import com.example.manager.service.EventService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/event")
@CrossOrigin(origins = "${cors.url}", maxAge = 3600, allowCredentials = "true")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class EventController {

    private final EventService eventService;

    @PostMapping("/create")
    public EventDTO createEvent (@Valid @RequestBody EventCreationDTO eventCreationDTO, BindingResult bindingResult) {
        ErrorHandler.checkForErrors(bindingResult, "Event creation failed");
        return eventService.createEvent(eventCreationDTO);
    }

    @PutMapping("/update")
    public EventDTO updateEvent (@Valid @RequestBody EventDTO eventDTO, BindingResult bindingResult) {
        ErrorHandler.checkForErrors(bindingResult, "Event update failed");
        return eventService.update(eventDTO);
    }

    @DeleteMapping("/delete")
    public void deleteEvent (@Valid @RequestBody EventDTO eventDTO, BindingResult bindingResult) {
        ErrorHandler.checkForErrors(bindingResult, "Event delete failed");
        eventService.delete(eventDTO.getId());
    }

}
