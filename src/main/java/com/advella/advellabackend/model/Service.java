package com.advella.advellabackend.model;

import com.fasterxml.jackson.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;
import java.sql.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "service_id")
    @ApiModelProperty(notes = "Service Id", example = "1", required = true)
    private int serviceId;
    @Column(name = "service_title")
    @ApiModelProperty(notes = "Service title", example = "Painting")
    private String title;
    @Column(name = "service_detail")
    @ApiModelProperty(notes = "Service detail", example = "I need to paint house")
    private String detail;
    @Column(name = "service_money_amount")
    @ApiModelProperty(notes = "Money amount", example = "100.0")
    private Float moneyAmount;
    @Column(name = "service_duration")
    @ApiModelProperty(notes = "Duration", example = "10")
    private Integer duration;
    @Column(name = "service_posted_datetime")
    @ApiModelProperty(notes = "Posted datetime", example = "1667477849")
    private Date postedDateTime;
    @Column(name = "service_deadline")
    @ApiModelProperty(notes = "Deadline", example = "1667477849")
    private Date deadline;
    @Column(name = "service_location")
    @ApiModelProperty(notes = "Service location", example = "Horsens,Midtjylland")
    private String location;
    @Column(name = "service_number_of_bids")
    @ApiModelProperty(notes = "Number od bids", example = "10")
    private Integer numberOfBids;
    @Column(name = "service_status")
    @ApiModelProperty(notes = "Service status", example = "open")
    private String serviceStatus;
    @OneToMany(mappedBy = "biddedService")
    @JsonManagedReference(value = "serviceBidServices")
    private List<BidService> bidServices;
    @ManyToOne
    @JoinColumn(name = "category_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer"})
    private ServiceCategory serviceCategory;
    @OneToMany(mappedBy = "reportedService")
    private List<ReportedService> reportedServices;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id")
    private User posted;
    @OneToMany(mappedBy = "serviceId")
    private List<ServiceImage> serviceImages;
}
