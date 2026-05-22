package com.example.Travel.destination;

import com.example.Travel.destination.dto.DestinationRequest;
import com.example.Travel.destination.dto.DestinationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class DestinationService {

    private final DestinationsRepository repository;
    //saving the destination into DB
    public DestinationResponse createDestination(DestinationRequest destinationRequest){
        DestinationEntity destination  = DestinationEntity.builder()
                .name(destinationRequest.getName())
                .country(destinationRequest.getCountry())
                .city(destinationRequest.getCity())
                .description(destinationRequest.getDescription())
                .imageUrl(destinationRequest.getImageUrl())
                .build();

        DestinationEntity saved = repository.save(destination);
        return toResponse(saved);
    }

    public List<DestinationResponse> getAll(){
        return repository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public DestinationResponse getById(UUID id){
        DestinationEntity destination = repository.findById(id)
                .orElseThrow(() -> new RuntimeException( " Destination does not Exist"));
        return toResponse(destination);
    }

    public List<DestinationResponse> getByName(String name){
        return repository.findByNameContainingIgnoreCase(name)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    //mapper function
    private DestinationResponse toResponse(DestinationEntity destination){

        return DestinationResponse.builder()
                .id(destination.getId())
                .name(destination.getName())
                .country(destination.getCountry())
                .city(destination.getCity())
                .description(destination.getDescription())
                .imageUrl(destination.getImageUrl())
                .build();
    }

}
