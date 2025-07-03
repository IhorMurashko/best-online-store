package com.beststore.Service;


import com.beststore.DTO.CategoryDTO;
import com.beststore.Mapper.CategoryEntityToDTOMapper;
import com.beststore.Repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryEntityToDTOMapper categoryEntityToDTOMapper;

    public List<CategoryDTO> getAllCategory() {
        return categoryRepository.findAll().stream().map(categoryEntityToDTOMapper::map).collect(Collectors.toList());
    }

}
