package com.beststore.Controller;


import com.beststore.DTO.BrandDTO;
import com.beststore.DTO.CategoryDTO;
import com.beststore.DTO.ProductDTO;
import com.beststore.DTO.ProductFilterRequest;
import com.beststore.Service.BrandService;
import com.beststore.Service.CatalogService;
import com.beststore.Service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/catalog")
public class CatalogController {

    private final CatalogService catalogService;
    private final CategoryService categoryService;
    private final BrandService brandService;

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

    @GetMapping("/category")
    public List<CategoryDTO> getAllCategory() {
        //TODO Дописать в сущность Images.
        return categoryService.getAllCategory();
    }

    @GetMapping("/brand")
    public List<BrandDTO> getAllBrand() {
        return brandService.getAllBrands();
    }

}
