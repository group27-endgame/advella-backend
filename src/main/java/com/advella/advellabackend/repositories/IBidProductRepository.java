package com.advella.advellabackend.repositories;

import com.advella.advellabackend.model.BidProduct;
import com.advella.advellabackend.model.BidService;
import com.advella.advellabackend.model.compositeKeys.BidProductId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IBidProductRepository extends JpaRepository<BidProduct, BidProductId> {
    @Query(value = "SELECT * FROM Bids_Product WHERE users_id = :userId AND product_id = :productId", nativeQuery = true)
    BidProduct getBidProduct(int productId, int userId);
}
