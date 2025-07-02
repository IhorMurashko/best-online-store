package com.beststore.Service;


import com.beststore.DTO.ProductDTO;
import com.beststore.Mapper.ProductEntityToDTOMapper;
import com.beststore.Repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CatalogService {

    private final ProductRepository catalogRepository;
    private final ProductEntityToDTOMapper productEntityToDTOMapper;

    public List<ProductDTO> getAllProduct() {
        return catalogRepository.findAll().stream().map(productEntityToDTOMapper::map).toList();
    }

    public List<ProductDTO> getAllProductPageable()
    {
        List<ProductDTO> allProduct = getAllProduct();


    }
}
