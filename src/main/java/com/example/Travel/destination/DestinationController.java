package com.example.Travel.destination;

import com.example.Travel.destination.dto.DestinationRequest;
import com.example.Travel.destination.dto.DestinationResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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
    public ResponseEntity<Page<DestinationResponse>> getAll(Pageable pageable){
        return new ResponseEntity<>(service.getAll(pageable) , HttpStatus.OK);
    }

    @GetMapping({"/{id}"})
    public ResponseEntity<DestinationResponse> getById(@PathVariable UUID id){
        DestinationResponse response = service.getById(id);
        return new ResponseEntity<>( response, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<DestinationResponse>> searchByName(@RequestParam String name , Pageable pageable){
        return new ResponseEntity<>(service.getByName(name , pageable) , HttpStatus.OK);
    }


}
