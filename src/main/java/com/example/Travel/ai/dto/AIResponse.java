package com.example.Travel.ai.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AIResponse implements Serializable {

//    @Serial
//    private static final long serialVersionUID = 1L;


    private String destination;
    private Integer budget;
    private Integer durationDays;
    private Itinerary itinerary;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Itinerary {

        private List<DayPlan> days;
        private Integer totalEstimatedCost;
        private List<String> tips;
        private String bestTimeToVisit;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DayPlan {

        private Integer day;
        private String theme;
        private List<Activity> activities;
        private Integer dailyBudget;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Activity {

        private String time;
        private String activity;
        private Integer estimatedCost;
    }
}

