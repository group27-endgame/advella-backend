package com.advella.advellabackend.repositories;

import com.advella.advellabackend.model.BidProduct;
import com.advella.advellabackend.model.compositeKeys.BidProductId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IBidProductRepository extends JpaRepository<BidProduct, BidProductId> {
}
