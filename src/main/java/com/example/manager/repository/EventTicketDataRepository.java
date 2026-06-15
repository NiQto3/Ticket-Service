package com.example.manager.repository;

import com.example.manager.model.EventTicketData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventTicketDataRepository extends JpaRepository<EventTicketData, Integer> {
    //Optional<EventTicketData> findByUsername(String username);
}