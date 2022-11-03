package com.advella.advellabackend.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private int productId;
    @Column(name = "product_title")
    private String title;
    @Column(name = "product_detail")
    private String detail;
    @Column(name = "product_money_amount")
    private Float moneyAmount;
    @Column(name = "product_pick_up_location")
    private String pickUpLocation;
    @Column(name = "product_posted_datetime")
    private Date postedDateTime;
    @Column(name = "product_deadline")
    private Date deadline;
    @Column(name = "product_status")
    private String productStatus;
    @Column(name = "product_number_of_bids")
    private Integer numberOfBids;
    @ManyToMany(mappedBy = "products")
    @JsonIgnore
    private List<User> users;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private ProductCategory productCategory;
    @OneToMany(mappedBy = "reportedProduct")
    private List<ReportedProduct> reportedProducts;
    @ManyToOne
    @JoinColumn(name = "users_id")
    private User posted;
    @OneToMany(mappedBy = "product")
    private List<ChatProduct> chatProducts;
}
