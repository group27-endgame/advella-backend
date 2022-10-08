package com.advella.advellabackend.model;

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
    @Column(name = "product_id")
    private int productId;
    @Column(name = "product_title")
    private String title;
    @Column(name = "product_detail")
    private String detail;
    @Column(name = "product_money_amount")
    private float moneyAmount;
    @Column(name = "product_pick_up_location")
    private String pickUpLocation;
    @Column(name = "product_posted_datetime")
    private Date postedDateTime;
    @Column(name = "product_deadline")
    private Date deadline;
    @Column(name = "product_number_of_bids")
    private int numberOfBids;
}
