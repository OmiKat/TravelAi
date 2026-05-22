package com.example.Travel.destination;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface DestinationsRepository extends JpaRepository<DestinationEntity , UUID> {
    List<DestinationEntity> findByCountry(String country);
    List<DestinationEntity> findByNameContainingIgnoreCase(String name);

}
