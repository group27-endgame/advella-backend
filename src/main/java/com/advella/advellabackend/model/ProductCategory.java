package com.advella.advellabackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "Categories_Product")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ProductCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private int productCategoryId;
    @Column(name = "category_title")
    private String title;
    @OneToMany(mappedBy = "productCategory")
    @JsonIgnore
    private List<Product> products;
}
