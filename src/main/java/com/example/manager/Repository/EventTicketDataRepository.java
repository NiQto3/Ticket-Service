package com.example.manager.Repository;

import com.example.manager.model.EventTicketData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EventTicketDataRepository extends JpaRepository<EventTicketData, Integer> {
    //Optional<EventTicketData> findByUsername(String username);
}