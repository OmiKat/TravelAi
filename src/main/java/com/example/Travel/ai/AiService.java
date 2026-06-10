package com.example.Travel.ai;

import com.example.Travel.ai.dto.AIRequest;
import com.example.Travel.ai.dto.AIResponse;
import com.example.Travel.ai.dto.GroqRequest;
import com.example.Travel.ai.dto.GroqResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AiService {

    private final PromptBuilder promptBuilder;

    @Value("${groq.api.url}")
    private String groqApiUrl;

    @Value("${groq.api.key}")
    private String groqApiKey;

    @Value("${groq.model}")
    private String groqModel;

    private final WebClient webClient = WebClient.builder().build();

    public AIResponse generateItinerary(AIRequest aiRequest){
        GroqRequest groqRequest = GroqRequest.builder()
                .model(groqModel)
                .messages(List.of(
                        GroqRequest.Message.builder()
                                .role("system")
                                .content(promptBuilder.buildSystemPrompt())
                                .build(),
                        GroqRequest.Message.builder()
                                .role("user")
                                .content(promptBuilder.buildUserPrompt(aiRequest))
                                .build()
                ))
                .build();

        GroqResponse groqResponse = webClient.post()
                .uri(groqApiUrl)
                .header("Authorization", "Bearer " + groqApiKey)
                .header("Content-Type", "application/json")
                .bodyValue(groqRequest)
                .retrieve()
//                .onStatus(
//                        HttpStatusCode::isError,
//                        response -> response.bodyToMono(String.class)
//                                .map(RuntimeException::new)
//                )
                .bodyToMono(GroqResponse.class)
                .block();

//        System.out.println(groqResponse);

        assert groqResponse != null;
        String itinerary = groqResponse.getGeneratedText();

        ObjectMapper mapper = new ObjectMapper();


        AIResponse.Itinerary itineraryObj =
                mapper.readValue(
                        itinerary,
                        AIResponse.Itinerary.class
                );


        return AIResponse.builder()
                .destination(aiRequest.getDestination())
                .budget(aiRequest.getBudget())
                .durationDays(aiRequest.getDurationDays())
                .itinerary(itineraryObj)
                .build();
    }









}
