package com.advella.advellabackend.model;

import lombok.*;

import javax.persistence.*;
import java.sql.Date;

@Getter
@Setter
@Entity
@Table(name = "Task_Services")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
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
}
