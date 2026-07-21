package com.example.manager.controller;

import com.example.manager.dto.LocationDTO;
import com.example.manager.dto.creation.LocationCreationDTO;
import com.example.manager.service.ErrorHandler;
import com.example.manager.service.LocationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/location")
@CrossOrigin(origins = "${cors.url}", maxAge = 3600, allowCredentials = "true")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class LocationController {

    private final LocationService locationService;

    @PostMapping("/create")
    public LocationDTO createLocation(@Valid @RequestBody LocationCreationDTO locationCreationDTO, BindingResult bindingResult) {
        ErrorHandler.checkForErrors(bindingResult, "Location creation failed");
        return locationService.createLocation(locationCreationDTO);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteLocation(@Valid @PathVariable Integer id) {
        locationService.delete(id);
    }

    @PutMapping("/update")
    public LocationDTO updateLocation(@Valid @RequestBody LocationDTO locationDTO, BindingResult bindingResult) {
        ErrorHandler.checkForErrors(bindingResult, "Location update failed");
        return locationService.update(locationDTO);
    }

}
