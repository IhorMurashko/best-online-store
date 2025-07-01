package com.beststore.DTO;

import com.beststore.Entity.Brand;
import com.beststore.Entity.Category;
import com.beststore.Entity.Image;
import com.beststore.Entity.Producer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    Long id;
    String productName;
    Double price;
    String description;
    Integer kcal;
    Brand brand;
    List<Image> images;
    Set<Category> categories;
    Producer producer;
}
