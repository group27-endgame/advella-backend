package com.advella.advellabackend.model;

import com.advella.advellabackend.model.compositeKeys.BidServiceId;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Bids_Service")
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class BidService implements Serializable {
    @EmbeddedId
    private BidServiceId id = new BidServiceId();

    @ManyToOne
    @MapsId("user")
    @JoinColumn(name = "users_id")
    @JsonBackReference(value = "userBidServices")
    private User serviceBidder;

    @ManyToOne
    @MapsId("service")
    @JoinColumn(name = "service_id")
    @JsonBackReference(value = "serviceBidServices")
    private Service biddedService;

    @Column(name = "bid_value")
    private Integer amount;
}
