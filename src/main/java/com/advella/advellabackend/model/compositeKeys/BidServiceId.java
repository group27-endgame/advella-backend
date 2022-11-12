package com.advella.advellabackend.model.compositeKeys;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class BidServiceId implements Serializable {
    @Column(name = "users_id")
    private Long user;
    @Column(name = "service_id")
    private Long service;
}
