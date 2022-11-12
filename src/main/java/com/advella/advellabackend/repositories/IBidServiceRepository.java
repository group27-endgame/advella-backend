package com.advella.advellabackend.repositories;

import com.advella.advellabackend.model.BidService;
import com.advella.advellabackend.model.compositeKeys.BidServiceId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IBidServiceRepository extends JpaRepository<BidService, BidServiceId> {
    @Query(value = "SELECT * FROM Bids_Service WHERE users_id = :userId AND service_id = :serviceId", nativeQuery = true)
    BidService getBidService(int serviceId, int userId);
}
