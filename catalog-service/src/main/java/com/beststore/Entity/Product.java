package com.beststore.Entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product extends Base {


    String productName;
    Double price;
    String description;
    Integer kcal;
    Integer quantityInStock;

    Double rating;
    Integer ratingCount;

    @OneToMany
    List<ProductSales> productSales;

    @ManyToOne
    @JoinColumn(name = "brand_id")
    Brand brand;

    @OneToMany(mappedBy = "product")
    @JsonManagedReference
    List<Image> images;

    @ManyToMany(mappedBy = "product")
    Set<Category> categories;

    @ManyToOne
    @JoinColumn(name = "producer_id")
    Producer producer;


}
