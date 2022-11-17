package com.advella.advellabackend.model.compositeKeys;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class BidServiceId implements Serializable {
    @Column(name = "users_id")
    private Integer user;
    @Column(name = "service_id")
    private Integer service;
}
