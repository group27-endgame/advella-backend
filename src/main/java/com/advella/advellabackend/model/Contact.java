package com.advella.advellabackend.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
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
    @Column(name = "contact_us_id")
    private int contactId;
    @Column(name = "message_datetime")
    private Date messageDateTime;
    @Column(name = "message_content")
    private String content;
    @Column(name = "seen")
    private Boolean isSeen;
}
