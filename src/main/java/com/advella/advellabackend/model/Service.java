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
@Table(name = "Task_Services")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "serviceId")
public class Service {
    @Id
    @Column(name = "service_id")
    private int serviceId;
    @Column(name = "service_title")
    private String title;
    @Column(name = "service_detail")
    private String detail;
    @Column(name = "service_money_amount")
    private float moneyAmount;
    @Column(name = "service_duration")
    private int duration;
    @Column(name = "service_posted_datetime")
    private Date postedDateTime;
    @Column(name = "service_deadline")
    private Date deadline;
    @Column(name = "service_location")
    private String location;
    @Column(name = "service_number_of_bids")
    private int numberOfBids;
    @Column(name = "service_number_of_likes")
    private int numberOfLikes;
    @Column(name = "service_status")
    private String serviceStatus;
    @ManyToMany(mappedBy = "services")
    private List<User> users;
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="category_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer"})
    private ServiceCategory serviceCategory;
}
