package com.advella.advellabackend.model;

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
public class User {
    @Id
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
}