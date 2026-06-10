package com.example.Travel.ai.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AIRequest {

    @NotBlank(message = "destination cannot blank")
    private String destination;

    @NotNull(message = "budget cannot be blank")
    private Integer budget;

    @NotNull(message = "budget cannot be blank")
    private Integer durationDays;

    private List<String> interests;

}
