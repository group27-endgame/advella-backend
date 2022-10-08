package com.advella.advellabackend.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

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
}
