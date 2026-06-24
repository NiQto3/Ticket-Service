package com.example.manager.repository;

import com.example.manager.model.EventTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventTicketRepository extends JpaRepository<EventTicket, Integer> {
}