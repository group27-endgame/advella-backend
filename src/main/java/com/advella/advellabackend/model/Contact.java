package com.advella.advellabackend.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;
import java.sql.Date;

@Getter
@Setter
@Entity
@Table(name = "Contact_Us")
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contact_us_id")
    @ApiModelProperty(notes = "Contact ID", example = "1", required = true)
    private int contactId;
    @Column(name = "message_datetime")
    @ApiModelProperty(notes = "Message date and time", example = "1667477849")
    private Date messageDateTime;
    @Column(name = "message_content")
    @ApiModelProperty(notes = "Message", example = "Hello")
    private String content;
    @Column(name = "seen")
    @ApiModelProperty(notes = "Is seen", example = "false")
    private Boolean isSeen;
    @ManyToOne
    @JoinColumn(name="users_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer"})
    private User contactUser;
}
