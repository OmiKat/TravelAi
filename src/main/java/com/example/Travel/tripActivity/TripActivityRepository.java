package com.example.Travel.tripActivity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TripActivityRepository extends JpaRepository<TripActivityEntity , UUID> {
    List<TripActivityEntity> findByTripId(UUID tripId);
}
