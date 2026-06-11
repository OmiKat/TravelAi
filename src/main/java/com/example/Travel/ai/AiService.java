package com.example.Travel.ai;

import com.example.Travel.ai.dto.AIRequest;
import com.example.Travel.ai.dto.AIResponse;
import com.example.Travel.ai.dto.GroqRequest;
import com.example.Travel.ai.dto.GroqResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import tools.jackson.databind.ObjectMapper;

import java.time.Duration;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AiService {

    private final PromptBuilder promptBuilder;
    private final RedisTemplate<String,Object> redisTemplate;
    private final ObjectMapper mapper;

    @Value("${groq.api.url}")
    private String groqApiUrl;

    @Value("${groq.api.key}")
    private String groqApiKey;

    @Value("${groq.model}")
    private String groqModel;

    @Value("${ai.cache.ttl}")
    private long cacheTtl;

    private final WebClient webClient = WebClient.builder().build();

    public AIResponse generateItinerary(AIRequest aiRequest){

        String cacheKey = "itinerary:" + aiRequest.toCacheKey();

        Object cached = redisTemplate.opsForValue().get(cacheKey);
        if (cached != null) {
            return mapper.convertValue(cached, AIResponse.class);
        }

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

//        ObjectMapper mapper = new ObjectMapper();


        AIResponse.Itinerary itineraryObj =
                mapper.readValue(
                        itinerary,
                        AIResponse.Itinerary.class
                );


        AIResponse response = AIResponse.builder()
                .destination(aiRequest.getDestination())
                .budget(aiRequest.getBudget())
                .durationDays(aiRequest.getDurationDays())
                .itinerary(itineraryObj)
                .build();

        redisTemplate.opsForValue().set(
                cacheKey,
                response,
                Duration.ofSeconds(cacheTtl)
        );

        return response;
    }









}
