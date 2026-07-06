package com.example.manager.repository;

import com.example.manager.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends JpaRepository<Location, Integer> {

    boolean existsByName(String email);
    boolean existsByAddress(String email);

}
