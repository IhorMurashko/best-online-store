package com.beststore.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "category")
public class Category extends Base {

    @Column(name = "category_name")
    String categoryName;

    @ManyToMany(mappedBy = "categories")
    List<Product> product;

    String image;
}
