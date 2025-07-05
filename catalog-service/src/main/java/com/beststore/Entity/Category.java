package com.beststore.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import lombok.*;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Category extends Base {

    String categoryName;

    @ManyToMany(mappedBy = "categories")
    List<Product> product;

    String image;
}
