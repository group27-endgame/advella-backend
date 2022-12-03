package com.advella.advellabackend.unit;

import com.advella.advellabackend.model.Rating;
import com.advella.advellabackend.repositories.IRatingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class RatingService {
    private final IRatingRepository ratingRepository;

    public List<Rating> getAllRatings() {
        return ratingRepository.findAll();
    }
}
