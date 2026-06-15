package com.example.manager.repository;

import com.example.manager.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer> {
    //Optional<Event> findByUsername(String username);
}
