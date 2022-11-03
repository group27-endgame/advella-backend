package com.advella.advellabackend.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rating_id")
    @ApiModelProperty(notes = "Ratting Id", example = "1", required = true)
    private int ratingId;
    @Column(name = "rating")
    @ApiModelProperty(notes = "Rating value", example = "5")
    private float rating;
    @Column(name = "votes")
    @ApiModelProperty(notes = "Number of votes", example = "2")
    private int votes;
    @ManyToOne
    @JoinColumn(name="users_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer"})
    private User ratingUser;
}
