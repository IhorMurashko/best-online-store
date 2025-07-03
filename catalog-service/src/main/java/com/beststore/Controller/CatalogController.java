package com.beststore.Controller;


import com.beststore.DTO.ProductDTO;
import com.beststore.DTO.ProductFilterRequest;
import com.beststore.Service.CatalogService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/catalog")
public class CatalogController {

    private final CatalogService catalogService;

    @GetMapping()
    public List<ProductDTO> getAllProduct()
    {
        return catalogService.getAllProduct();
    }



    @PostMapping("/filter")
    public Page<ProductDTO> getAllProduct(
            @RequestBody(required = false) ProductFilterRequest productFilterRequest)
    {
        return catalogService.getAllProductPageable(productFilterRequest);
    }



}
