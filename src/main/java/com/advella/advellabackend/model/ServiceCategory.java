package com.advella.advellabackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "Categories_Service")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ServiceCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    @ApiModelProperty(notes = "Category Id", example = "1", required = true)
    private int serviceCategoryId;
    @Column(name = "category_title")
    @ApiModelProperty(notes = "Category title", example = "Artistic jobs")
    private String title;
    @OneToMany(mappedBy = "serviceCategory")
    @JsonIgnore
    private List<Service> services;
}
