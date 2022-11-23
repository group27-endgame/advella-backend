package com.advella.advellabackend.model;

import com.advella.advellabackend.model.chat.ChatMessage;
import com.advella.advellabackend.model.chat.ChatRoom;
import com.fasterxml.jackson.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Users")
@EqualsAndHashCode
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "products", "services"})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "users_id")
    @ApiModelProperty(notes = "User Id", example = "1", required = true)
    private int userId;
    @Column(name = "email")
    @ApiModelProperty(notes = "Email", example = "example@gmail.com")
    private String email;
    @Column(name = "user_password")
    @ApiModelProperty(notes = "Password", example = "123456")
    private String password;
    @Column(name = "username")
    @ApiModelProperty(notes = "Username", example = "C0bra")
    private String username;
    @Column(name = "user_description")
    @ApiModelProperty(notes = "Description", example = "Normal user of this app")
    private String description;
    @Column(name = "user_location")
    @ApiModelProperty(notes = "Location", example = "Horsens,Midtjylland")
    private String location;
    @Column(name = "registration_datetime")
    @ApiModelProperty(notes = "Registration datetime", example = "1667477849")
    private Date registrationDateTime;
    @ManyToMany
    @JoinTable(name = "Users_Roles",
            joinColumns = @JoinColumn(name = "users_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles;
    @JsonManagedReference(value = "userBidProducts")
    @OneToMany(mappedBy = "productBidder")
    private List<BidProduct> bidProducts;
    @OneToMany(mappedBy = "serviceBidder")
    @JsonManagedReference(value = "userBidServices")
    private List<BidService> bidServices;
    @OneToMany(mappedBy = "contactUser")
    private List<Contact> contact;
    @OneToMany(mappedBy = "ratingUser")
    private List<Rating> ratings;
    @OneToMany(mappedBy = "productReportUser")
    private List<ReportedProduct> reportedProducts;
    @OneToMany(mappedBy = "serviceReportUser")
    private List<ReportedService> reportedServices;
    @OneToMany(mappedBy = "posted", fetch = FetchType.EAGER)
    private List<Service> postedService;
    @OneToMany(mappedBy = "posted")
    private List<Product> postedProduct;
    @OneToMany(mappedBy = "chatMessageSender")
    private List<ChatMessage> sendMessages;
    @OneToMany(mappedBy = "chatMessageRecipient")
    private List<ChatMessage> receivedMessage;
    @OneToMany(mappedBy = "chatSender")
    private List<ChatRoom> sendChatRoom;
    @OneToMany(mappedBy = "chatRecipient")
    private List<ChatRoom> receivedChatRoom;

    public User(int userId, String username) {
        this.userId = userId;
        this.username = username;
    }
}
