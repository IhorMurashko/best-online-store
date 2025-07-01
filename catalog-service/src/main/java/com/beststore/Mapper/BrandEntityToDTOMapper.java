package com.beststore.Mapper;

import com.beststore.DTO.BrandDTO;
import com.beststore.Entity.Brand;
import org.springframework.stereotype.Component;

@Component
public class BrandEntityToDTOMapper implements BaseMapper<Brand, BrandDTO>{
    @Override
    public BrandDTO map(Brand obj) {
        return BrandDTO.builder()
                .brandName(obj.getBrandName())
                .build();
    }
}
