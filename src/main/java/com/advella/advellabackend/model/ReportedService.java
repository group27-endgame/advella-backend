package com.advella.advellabackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reported_service_id")
    @ApiModelProperty(notes = "Reported service Id", example = "1", required = true)
    private int reportedServiceId;
    @Column(name = "reported_datetime")
    @ApiModelProperty(notes = "Reported datetime", example = "1667477849")
    private Date reportedDateTime;
    @Column(name = "reason")
    @ApiModelProperty(notes = "Reason", example = "Fake")
    private String reason;
    @ManyToOne
    @JoinColumn(name="service_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer"})
    private Service reportedService;
    @ManyToOne
    @JoinColumn(name="users_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer"})
    @JsonIgnore
    private User serviceReportUser;
}
