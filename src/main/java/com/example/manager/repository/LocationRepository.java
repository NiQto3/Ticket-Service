package com.example.manager.repository;

import com.example.manager.model.Location;
import com.example.manager.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocationRepository extends JpaRepository<Location, Integer> {

    boolean existsByName(String email);
    boolean existsByAddress(String email);

}
