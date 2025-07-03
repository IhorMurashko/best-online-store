package com.beststore.Service;

import com.beststore.DTO.BrandDTO;
import com.beststore.Mapper.BrandEntityToDTOMapper;
import com.beststore.Repository.BrandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BrandService {

    private final BrandRepository brandRepository;
    private final BrandEntityToDTOMapper brandEntityToDTOMapper;

    public List<BrandDTO> getAllBrands() {
        return  brandRepository.findAll().stream().map(brandEntityToDTOMapper::map).collect(Collectors.toList());
    }



}
