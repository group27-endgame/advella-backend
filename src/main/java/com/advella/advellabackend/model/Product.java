package com.advella.advellabackend.model;

import com.fasterxml.jackson.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Products")
@EqualsAndHashCode
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    @ApiModelProperty(notes = "Product Id", example = "1", required = true)
    private int productId;
    @Column(name = "product_title")
    @ApiModelProperty(notes = "Product title", example = "New Product")
    private String title;
    @Column(name = "product_detail")
    @ApiModelProperty(notes = "Detail of product", example = "Vacuum cleaner")
    private String detail;
    @Column(name = "product_money_amount")
    @ApiModelProperty(notes = "Money amount", example = "100.0")
    private Float moneyAmount;
    @Column(name = "product_pick_up_location")
    @ApiModelProperty(notes = "Pickup location", example = "Horsens, Midtjylland")
    private String pickUpLocation;
    @Column(name = "product_posted_datetime")
    @ApiModelProperty(notes = "Posted datetime", example = "1667477849")
    private Date postedDateTime;
    @Column(name = "product_deadline")
    @ApiModelProperty(notes = "Product deadline", example = "1667477849")
    private Date deadline;
    @Column(name = "product_status")
    @ApiModelProperty(notes = "Product status", example = "open")
    private String productStatus;
    @Column(name = "product_number_of_bids")
    @ApiModelProperty(notes = "Number of bids", example = "10")
    private Integer numberOfBids;
    @OneToMany(mappedBy = "biddedProduct")
    @JsonManagedReference(value = "productBidProducts")
    private List<BidProduct> bidProducts;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private ProductCategory productCategory;
    @OneToMany(mappedBy = "reportedProduct")
    private List<ReportedProduct> reportedProducts;
    @ManyToOne
    @JoinColumn(name = "users_id")
    private User posted;
    @OneToMany(mappedBy = "productId")
    private List<ProductImage> productImages;
}
