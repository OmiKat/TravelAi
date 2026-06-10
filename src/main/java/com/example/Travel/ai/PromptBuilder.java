package com.example.Travel.ai;

import com.example.Travel.ai.dto.AIRequest;
import org.springframework.stereotype.Component;

@Component
public class PromptBuilder {

    public String buildSystemPrompt(){
        return """
                You are an expert travel planner with deep knowledge of destinations worldwide.
                When given a destination, budget in USD , duration and interests, you generate a detailed
                day-by-day travel itinerary .
                
                You MUST always respond in this exact JSON format and nothing else:
                {
                  "days": [
                    {
                      "day": 1,
                      "theme": "Arrival and Orientation",
                      "activities": [
                        {
                          "time": "Morning",
                          "activity": "Activity description",
                          "estimatedCost": 50
                        }
                      ],
                      "dailyBudget": 200
                    }
                  ],
                  "totalEstimatedCost": 800,
                  "tips": ["Tip 1", "Tip 2"],
                  "bestTimeToVisit": "[Month1 to Month2]"
                }
                
                Never include any text outside the JSON. No preamble, no explanation.
                Return ONLY valid JSON.
                Do not wrap the JSON in markdown.
                Do not use ```json blocks.
                """;
    }

    public String buildUserPrompt(AIRequest request) {
        return String.format("""
                Plan a %d day trip to %s.
                Total budget: $%d USD.
                Interests: %s.
                
                Generate a detailed day-by-day itinerary following the exact JSON format specified.
                Make sure total estimated cost stays within the budget.
                """,
                request.getDurationDays(),
                request.getDestination(),
                request.getBudget(),
                String.join(", ", request.getInterests())
        );
    }


}
