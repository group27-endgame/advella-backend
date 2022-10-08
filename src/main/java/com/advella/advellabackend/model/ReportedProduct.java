package com.advella.advellabackend.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Reported_Products")
public class ReportedProduct {
    @Id
    @Column(name = "reported_product_id")
    private int reportedProductId;
    @Column(name = "reported_datetime")
    private Date reportedDateTime;
    @Column(name = "reason")
    private String reason;
}