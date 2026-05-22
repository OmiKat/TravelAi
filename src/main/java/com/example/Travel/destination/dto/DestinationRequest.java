package com.example.Travel.destination.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DestinationRequest {

    @NotBlank(message = "name is required")
    private String name;

    @NotBlank(message = "country is required")
    private String country;
    
    @NotBlank(message = "city is required")
    private String city;

    private String description;
    private String imageUrl;

}
