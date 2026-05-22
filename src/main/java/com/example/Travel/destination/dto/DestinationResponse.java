package com.example.Travel.destination.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DestinationResponse {
    
    private UUID id;
    private String name;
    private String country;
    private String city;
    private String description;
    private String imageUrl;

}
