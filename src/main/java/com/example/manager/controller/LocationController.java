package com.example.manager.controller;

import com.example.manager.dto.LocationDTO;
import com.example.manager.dto.creation.LocationCreationDTO;
import com.example.manager.service.LocationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/location")
@CrossOrigin(origins = "${cors.url}", maxAge = 3600, allowCredentials = "true")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class LocationController {

    private final LocationService locationService;

    @PutMapping("/")
    public LocationDTO createLocation(@Valid @RequestBody LocationCreationDTO locationCreationDTO, BindingResult bindingResult) {
        ErrorHandler.checkForErrors(bindingResult, "Location creation failed");
        return locationService.createLocation(locationCreationDTO);
    }

    @DeleteMapping("/")
    public void deleteLocation(@Valid @RequestBody LocationDTO locationDTO, BindingResult bindingResult) {
        ErrorHandler.checkForErrors(bindingResult, "Location delete failed");
        locationService.delete(locationDTO.getId());
    }

    @PutMapping("/")
    public LocationDTO updateLocation(@Valid @RequestBody LocationDTO locationDTO, BindingResult bindingResult) {
        ErrorHandler.checkForErrors(bindingResult, "Location update failed");
        return locationService.update(locationDTO);
    }

}
