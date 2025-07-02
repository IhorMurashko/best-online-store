package com.beststore.Controller;


import com.beststore.DTO.ProductDTO;
import com.beststore.Service.CatalogService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/catalog")
public class CatalogController {

    private final CatalogService catalogService;

@GetMapping()
public List<ProductDTO> getAllProduct(
    @RequestParam(defaultValue = "0") int page,
    @RequestParam(defaultValue = "24") int size)
    {
        return catalogService.getAllProductPageable();
    }
}
