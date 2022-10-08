package com.advella.advellabackend.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Ratings")
@Entity
@EqualsAndHashCode
public class Rating {
    @Id
    @Column(name = "rating_id")
    private int ratingId;
    @Column(name = "rating")
    private float rating;
    @Column(name = "votes")
    private int votes;
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="users_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer"})
    private User ratingUser;
}
