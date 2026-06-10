package com.example.Travel.trip;

import com.example.Travel.trip.dto.TripRequest;
import com.example.Travel.trip.dto.TripResponse;
import com.example.Travel.user.UserEntity;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/trip")
@RequiredArgsConstructor
public class TripController {

    private final TripService tripService;

    @PostMapping("/create")
    public ResponseEntity<TripResponse> createTrip(
            @RequestBody @Valid TripRequest tripRequest,
            @AuthenticationPrincipal UserDetails userDetails){

        UserEntity user = (UserEntity) userDetails;

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(tripService.createTrip(tripRequest,user.getId()));

    }

    @GetMapping
    public ResponseEntity<List<TripResponse>> getMyTrips(@AuthenticationPrincipal UserDetails userDetails){
        UserEntity user = (UserEntity) userDetails;

        return ResponseEntity.ok(tripService.getTripsByUser(user.getId()));
    }

}
