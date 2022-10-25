package com.advella.advellabackend.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
    private int contactId;
    @Column(name = "message_datetime")
    private Date messageDateTime;
    @Column(name = "message_content")
    private String content;
    @Column(name = "seen")
    private Boolean isSeen;
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="users_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer"})
    private User contactUser;
}
