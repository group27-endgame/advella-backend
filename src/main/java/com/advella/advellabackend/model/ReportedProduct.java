package com.advella.advellabackend.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Reported_Products")
public class ReportedProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reported_product_id")
    private int reportedProductId;
    @Column(name = "reported_datetime")
    private Date reportedDateTime;
    @Column(name = "reason")
    private String reason;
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="product_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer"})
    private Product reportedProduct;
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="users_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer"})
    private User productReportUser;
}
