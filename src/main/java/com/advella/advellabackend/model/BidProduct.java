package com.advella.advellabackend.model;

import com.advella.advellabackend.model.compositeKeys.BidProductId;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Bids_Product")
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class BidProduct implements Serializable {
    @EmbeddedId
    private BidProductId id = new BidProductId();

    @ManyToOne
    @MapsId("user")
    @JoinColumn(name = "users_id")
    @JsonBackReference(value = "userBidProducts")
    private User productBidder;

    @ManyToOne
    @MapsId("product")
    @JoinColumn(name = "product_id")
    @JsonBackReference(value = "productBidProducts")
    private Product biddedProduct;

    @Column(name = "bid_value")
    private Integer amount;
}
