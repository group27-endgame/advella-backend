package com.advella.advellabackend.model;

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
    @Column(name = "category_id")
    private int serviceCategoryId;
    @Column(name = "category_title")
    private String title;
    @OneToMany(mappedBy = "serviceCategory")
    private List<Service> services;
}
