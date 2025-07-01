package com.beststore.Mapper;

import com.beststore.DTO.ProductSalesDTO;
import com.beststore.Entity.ProductSales;
import org.springframework.stereotype.Component;

@Component
public class ProductSalesEntityToDTOMapper implements BaseMapper<ProductSales, ProductSalesDTO>{
    @Override
    public ProductSalesDTO map(ProductSales obj) {
        return ProductSalesDTO.builder()
                .productId(obj.getId())
                .quantity(obj.getQuantity())
                .saleDate(obj.getSaleDate())
                .build();
    }
}
