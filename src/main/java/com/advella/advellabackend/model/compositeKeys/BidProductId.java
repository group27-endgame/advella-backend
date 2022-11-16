package com.advella.advellabackend.model.compositeKeys;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BidProductId implements Serializable {
    @Column(name = "users_id")
    private Integer user;
    @Column(name = "product_id")
    private Integer product;
}
