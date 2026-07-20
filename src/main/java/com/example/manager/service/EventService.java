package com.example.manager.service;

import com.example.manager.dto.EventDTO;
import com.example.manager.dto.creation.EventCreationDTO;
import com.example.manager.mapper.EventMapper;
import com.example.manager.model.Event;
import com.example.manager.model.Location;
import com.example.manager.repository.EventRepository;
import com.example.manager.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class EventService {

    private final EventRepository eventRepository;
    private final UserService userService;
    private final LocationService locationService;
    private final EventMapper eventMapper;

    @Transactional
    public EventDTO createEvent(EventCreationDTO eventCreation) {

        Event event = Event.builder()
                .datetime(eventCreation.getDatetime())
                .organizer(userService.findUserById(eventCreation.getOrganizerId()))
                .location(locationService.findLocationById(eventCreation.getLocationId()))
                .build();
        return eventMapper.toDto(eventRepository.save(event));
    }

    public Event findEventById(int id) {
        var eventOptional = eventRepository.findById(id);
        return eventOptional.orElseThrow(() -> new RuntimeException("Event not found"));
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN') or hasRole('EVENT_MANAGER')")
    public void delete(int id) {
        eventRepository.deleteById(id);
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN') or hasRole('EVENT_MANAGER')")
    public EventDTO update(EventDTO eventDTO) {
        final Event event = findEventById(eventDTO.getId());
        return eventMapper.toDto(eventRepository.save(
                merge(event, findEventById(event.getId()))));
    }

    private Event merge (Event src, Event dest) {
        if (src.getDatetime() != null) {
            dest.setDatetime(src.getDatetime());
        }
        if (src.getLocation() != null) {
            dest.setLocation(src.getLocation());
        }
        if (src.getOrganizer() != null) {
            dest.setOrganizer(src.getOrganizer());
        }
        return dest;
    }

}
