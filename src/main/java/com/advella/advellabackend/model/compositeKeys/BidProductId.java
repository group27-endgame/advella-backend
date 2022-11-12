package com.advella.advellabackend.model.compositeKeys;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class BidProductId implements Serializable {
    @Column(name = "users_id")
    private Long user;
    @Column(name = "product_id")
    private Long product;
}
