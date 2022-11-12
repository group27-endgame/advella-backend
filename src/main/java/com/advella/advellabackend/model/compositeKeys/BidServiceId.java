package com.advella.advellabackend.model.compositeKeys;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class BidServiceId implements Serializable {
    @Column(name = "users_id")
    private Integer user;
    @Column(name = "service_id")
    private Integer service;
}
