package com.beststore.Mapper;

import com.beststore.DTO.BrandDTO;
import com.beststore.DTO.CategoryDTO;
import com.beststore.Entity.Brand;
import com.beststore.Entity.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryEntityToDTOMapper implements BaseMapper<Category, CategoryDTO>{
    @Override
    public CategoryDTO map(Category obj) {
        return CategoryDTO.builder()
                .category_name(obj.getCategory_name())
                .build();
    }
}
