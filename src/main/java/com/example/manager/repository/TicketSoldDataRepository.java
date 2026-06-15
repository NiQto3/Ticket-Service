package com.example.manager.repository;

import com.example.manager.model.TicketSoldData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketSoldDataRepository extends JpaRepository<TicketSoldData, Integer> {
    //Optional<TicketSoldData> findByUsername(String username);
}
