package com.example.Travel.destination;

import com.example.Travel.destination.dto.DestinationRequest;
import com.example.Travel.destination.dto.DestinationResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/destinations")
public class DestinationController {

    private final DestinationService service;

    @PostMapping()
    public ResponseEntity<DestinationResponse> createDestination(
            @RequestBody @Valid DestinationRequest request){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.createDestination(request));

    }

    @GetMapping()
    public ResponseEntity<List<DestinationResponse>> getAll(){
        return ResponseEntity.ok(service.getAll());
    }



}
