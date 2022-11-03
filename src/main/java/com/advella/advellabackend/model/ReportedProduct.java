package com.advella.advellabackend.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;
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
    @ApiModelProperty(notes = "Reported product Id", example = "1", required = true)
    private int reportedProductId;
    @Column(name = "reported_datetime")
    @ApiModelProperty(notes = "Reported datetime", example = "1667477849")
    private Date reportedDateTime;
    @Column(name = "reason")
    @ApiModelProperty(notes = "Reason", example = "Faulty")
    private String reason;
    @ManyToOne
    @JoinColumn(name="product_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer"})
    private Product reportedProduct;
    @ManyToOne
    @JoinColumn(name="users_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer"})
    private User productReportUser;
}
