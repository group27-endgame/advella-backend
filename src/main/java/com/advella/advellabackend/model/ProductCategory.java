package com.advella.advellabackend.model;

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
    @Column(name = "category_id")
    private int productCategoryId;
    @Column(name = "category_title")
    private String title;
    @OneToMany(mappedBy = "productCategory")
    private List<Product> products;
}
