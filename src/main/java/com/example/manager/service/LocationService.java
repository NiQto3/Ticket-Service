package com.example.manager.service;

import com.example.manager.dto.creation.LocationCreationDTO;
import com.example.manager.model.Location;
import com.example.manager.model.User;
import com.example.manager.repository.LocationRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LocationService {

    private final LocationRepository locationRepository;

    @Transactional
    public Location createLocation(LocationCreationDTO locationCreation) {

        if (locationRepository.existsByAddress(locationCreation.getAddress()) &&
            locationRepository.existsByName(locationCreation.getName())) {
            throw new RuntimeException("Location already exists");
        }

        Location location = Location.builder()
                .name(locationCreation.getName())
                .address(locationCreation.getAddress())
                .build();
        locationRepository.save(location);

        return location;
    }

    public Location findLocationById(int id) {
        var locationOptional = locationRepository.findById(id);
        return locationOptional.orElseThrow(() -> new RuntimeException("Location not found"));
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN') or hasRole('EVENT_MANAGER')")
    public void delete(int id) {
        locationRepository.deleteById(id);
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN') or hasRole('EVENT_MANAGER')")
    public Location update(Location location) {
        return locationRepository.save(
                merge(findLocationById(
                        location.getId()), location)
        );
    }

    private Location merge (Location src, Location dest) {
        if (src.getName() != null) {
            dest.setName(src.getName());
        }
        if (src.getAddress() != null) {
            dest.setAddress(src.getAddress());
        }
        return dest;
    }

}
