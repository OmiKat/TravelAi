package com.example.Travel.ai.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GroqRequest {
    private String model;
    private List<Message> messages;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Message{
        private String role;
        private String content;

    }

}
