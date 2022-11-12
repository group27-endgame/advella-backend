package com.advella.advellabackend.repositories;

import com.advella.advellabackend.model.BidService;
import com.advella.advellabackend.model.compositeKeys.BidServiceId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IBidServiceRepository extends JpaRepository<BidService, BidServiceId> {
}
