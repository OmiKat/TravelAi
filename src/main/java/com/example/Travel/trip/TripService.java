package com.example.Travel.trip;

import com.example.Travel.destination.DestinationEntity;
import com.example.Travel.destination.DestinationsRepository;
import com.example.Travel.destination.dto.DestinationResponse;
import com.example.Travel.trip.dto.TripRequest;
import com.example.Travel.trip.dto.TripResponse;
import com.example.Travel.user.UserEntity;
import com.example.Travel.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TripService {

    private final TripRepository tripRepository;
    private final DestinationsRepository destinationsRepository;
    private final UserRepository userRepository;


    @Transactional
    public TripResponse createTrip(TripRequest tripRequest , UUID userID){

        UserEntity user = userRepository.findById(userID)
                .orElseThrow(() -> new RuntimeException("User not found"));

        DestinationEntity destination = destinationsRepository
                .findById(tripRequest.getDestinationId())
                .orElseThrow(() -> new RuntimeException("Destination not found"));

        TripEntity trip = TripEntity.builder()
                .title(tripRequest.getTitle())
                .startDate(tripRequest.getStartDate())
                .endDate(tripRequest.getEndDate())
                .budget(tripRequest.getBudget())
                .user(user)
                .destination(destination)
                .build();

        TripEntity saved  = tripRepository.save(trip);
        return toResponse(saved);

    }
    @Transactional(readOnly = true)
    public List<TripResponse> getTripsByUser(UUID userId){
        return tripRepository.findByUserId(userId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    private TripResponse toResponse(TripEntity trip){

        DestinationResponse destination = DestinationResponse.builder()
                .id(trip.getDestination().getId())
                .name(trip.getDestination().getName())
                .country(trip.getDestination().getCountry())
                .city(trip.getDestination().getCity())
                .description(trip.getDestination().getDescription())
                .imageUrl(trip.getDestination().getImageUrl())
                .build();


        return TripResponse.builder()
                .id(trip.getId())
                .title(trip.getTitle())
                .startDate(trip.getStartDate())
                .endDate(trip.getEndDate())
                .budget(trip.getBudget())
                .destinationResponse(destination)
                .build();

    }

}
