package com.advella.advellabackend.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
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
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "productId")
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
    @ManyToMany(mappedBy = "products")
    private List<User> users;
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="category_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer"})
    private ProductCategory productCategory;
}
