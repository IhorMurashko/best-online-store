package com.beststore.Controller;


import com.beststore.DTO.ProductDTO;
import com.beststore.Service.CatalogService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CatalogController {

    private final CatalogService catalogService;

    @GetMapping("api/catalog/")
    public List<ProductDTO> getAllCatalog() {
        return catalogService.getAllProduct();
    }
}
