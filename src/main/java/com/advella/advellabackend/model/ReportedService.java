package com.advella.advellabackend.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@Table(name = "Reported_Services")
public class ReportedService {
    @Id
    @Column(name = "reported_service_id")
    private int reportedServiceId;
    @Column(name = "reported_datetime")
    private Date reportedDateTime;
    @Column(name = "reason")
    private String reason;
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="service_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer"})
    private Service reportedService;
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="users_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer"})
    private User serviceReportUser;
}
