package com.example.Travel.trip.dto;

import com.example.Travel.destination.dto.DestinationResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TripResponse {
    private UUID id;
    private String title;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Long budget;
    private DestinationResponse destinationResponse;
}
