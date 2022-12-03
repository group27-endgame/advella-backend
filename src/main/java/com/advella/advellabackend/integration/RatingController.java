package com.advella.advellabackend.integration;

import com.advella.advellabackend.model.Rating;
import com.advella.advellabackend.unit.RatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class RatingController {
    private final RatingService ratingService;

    @GetMapping("/ratings")
    public ResponseEntity<List<Rating>> getAllRatings() {
        return ResponseEntity.ok(ratingService.getAllRatings());
    }
}
