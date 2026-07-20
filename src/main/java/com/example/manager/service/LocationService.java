package com.example.manager.service;

import com.example.manager.dto.LocationDTO;
import com.example.manager.dto.creation.LocationCreationDTO;
import com.example.manager.mapper.LocationMapper;
import com.example.manager.model.Location;
import com.example.manager.model.User;
import com.example.manager.repository.LocationRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class LocationService {

    private final LocationRepository locationRepository;
    private final LocationMapper locationMapper;

    @Transactional
    public LocationDTO createLocation(LocationCreationDTO locationCreation) {

        if (locationRepository.existsByAddress(locationCreation.getAddress()) &&
            locationRepository.existsByName(locationCreation.getName())) {
            throw new RuntimeException("Location already exists");
        }

        Location location = Location.builder()
                .name(locationCreation.getName())
                .address(locationCreation.getAddress())
                .build();

        return locationMapper.toDto(locationRepository.save(location));
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
    public LocationDTO update(LocationDTO locationDto) {
        final Location location = findLocationById(locationDto.getId());
        return locationMapper.toDto(locationRepository.save(
                merge(location, findLocationById(location.getId()))));
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
