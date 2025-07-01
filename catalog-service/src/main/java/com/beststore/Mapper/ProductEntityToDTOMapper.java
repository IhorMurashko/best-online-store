package com.beststore.Mapper;

import com.beststore.DTO.ProductDTO;
import com.beststore.Entity.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductEntityToDTOMapper implements BaseMapper<Product, ProductDTO> {
    @Override
    public ProductDTO map(Product obj) {
        return ProductDTO.builder()
                .id(obj.getId())
                .price(obj.getPrice())
                .kcal(obj.getKcal())
                .productName(obj.getProductName())
                .categories(obj.getCategories())
                .description(obj.getDescription())
                .images(obj.getImages())
                .producer(obj.getProducer())
                .brand(obj.getBrand())
                .build();
    }
}
