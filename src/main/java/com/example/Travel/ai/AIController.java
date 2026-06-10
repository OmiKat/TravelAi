package com.example.Travel.ai;

import com.example.Travel.ai.dto.AIRequest;
import com.example.Travel.ai.dto.AIResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/ai")
@RequiredArgsConstructor
public class AIController {

    private final AiService aiService;

    @PostMapping("/itinerary")
    public ResponseEntity<AIResponse> generateItinerary(@RequestBody @Valid AIRequest aiRequest){
        return ResponseEntity.ok(aiService.generateItinerary(aiRequest));
    }

}
