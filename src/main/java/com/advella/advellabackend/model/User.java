package com.advella.advellabackend.model;

import com.fasterxml.jackson.annotation.*;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Users")
@EqualsAndHashCode
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "userId")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "roles", "products", "services"})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "users_id")
    private int userId;
    @Column(name = "email")
    private String email;
    @Column(name = "user_password")
    private String password;
    @Column(name = "username")
    private String username;
    @Column(name = "user_description")
    private String description;
    @Column(name = "user_location")
    private String location;
    @Column(name = "registration_datetime")
    private Date registrationDateTime;
    @ManyToMany
    @JoinTable(name = "Users_Roles",
            joinColumns = @JoinColumn(name = "users_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles;
    @ManyToMany
    @JoinTable(name = "Bids_Product",
            joinColumns = @JoinColumn(name = "users_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id"))
    private List<Product> products;
    @ManyToMany
    @JoinTable(name = "Bids_Service",
            joinColumns = @JoinColumn(name = "users_id"),
            inverseJoinColumns = @JoinColumn(name = "service_id"))
    private List<Service> services;
    @OneToMany(mappedBy = "contactUser")
    private List<Contact> contact;
    @OneToMany(mappedBy = "ratingUser")
    private List<Rating> ratings;
    @OneToMany(mappedBy = "productReportUser")
    private List<ReportedProduct> reportedProducts;
    @OneToMany(mappedBy = "serviceReportUser")
    private List<ReportedService> reportedServices;
    @OneToMany(mappedBy = "posted")
    private List<Service> postedService;
    @OneToMany(mappedBy = "posted")
    private List<Product> postedProduct;
    @OneToMany(mappedBy = "userBidder")
    private List<ChatService> bidderChatService;
    @OneToMany(mappedBy = "userPoster")
    private List<ChatService> posterChatService;
    @OneToMany(mappedBy = "userBidder")
    private List<ChatProduct> bidderChatProduct;
    @OneToMany(mappedBy = "userPoster")
    private List<ChatProduct> posterChatProduct;
}
