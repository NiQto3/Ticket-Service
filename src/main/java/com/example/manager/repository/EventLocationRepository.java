package com.example.manager.repository;

import com.example.manager.model.EventLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventLocationRepository extends JpaRepository<EventLocation, Integer> {
    //Optional<EventLocation> findByUsername(String username);
}
