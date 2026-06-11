package com.example.manager.Repository;

import com.example.manager.model.EventLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EventLocationRepository extends JpaRepository<EventLocation, Integer> {
    //Optional<EventLocation> findByUsername(String username);
}
