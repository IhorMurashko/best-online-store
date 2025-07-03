package com.beststore.Service;


import com.beststore.DTO.ProductDTO;
import com.beststore.DTO.ProductFilterRequest;
import com.beststore.DTO.ProductSalesDTO;
import com.beststore.Mapper.ProductEntityToDTOMapper;
import com.beststore.Repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CatalogService {

    private final ProductRepository catalogRepository;
    private final ProductEntityToDTOMapper productEntityToDTOMapper;

    public List<ProductDTO> getAllProduct() {
        return catalogRepository.findAll().stream().map(productEntityToDTOMapper::map).toList();
    }



    public Page<ProductDTO> getAllProductPageable(ProductFilterRequest productFilterRequest)
    {
        List<ProductDTO> allProduct = getAllProduct();


        List<ProductDTO> filtered = applyFilters(allProduct, productFilterRequest);
        applySorting(filtered, productFilterRequest);

        return paginate(filtered, productFilterRequest.getPage(), productFilterRequest.getSize());


    }

    private List<ProductDTO> applyFilters(List<ProductDTO> products, ProductFilterRequest f) {
        return products.stream()
                .filter(p -> f.getAZ() == null || p.getProductName().charAt(0) == f.getAZ())
                .filter(p -> f.getCategoryName() == null || p.getCategories().stream()
                        .anyMatch(c -> c.getCategory_name().equalsIgnoreCase(f.getCategoryName())))
                .filter(p -> f.getBrand() == null || p.getBrand().getBrandName().equalsIgnoreCase(f.getBrand()))
                .filter(p -> f.getPriceFrom() == null || p.getPrice() >= f.getPriceFrom())
                .filter(p -> f.getPriceTo() == null || p.getPrice() <= f.getPriceTo())
                .collect(Collectors.toList());
    }



    private void applySorting(List<ProductDTO> products, ProductFilterRequest f) {
        String sortBy = f.getSortBy();
        Comparator<ProductDTO> comparator = null;

        if (sortBy != null) {
            switch (sortBy) {
                case "priceAsc" -> comparator = Comparator.comparing(p -> p.getPrice());
                case "priceDesc" -> comparator = Comparator.comparing((ProductDTO p) -> p.getPrice()).reversed();
                case "rating" -> comparator = Comparator.comparing((ProductDTO p) -> p.getRating()).reversed();
                case "popular" -> comparator = Comparator.comparing((ProductDTO p) -> p.getProductSales().stream()
                        .mapToInt(ProductSalesDTO::getQuantity)
                        .max()
                        .orElse(0)
                ).reversed();
            }
        }

        if (comparator != null) {
            products.sort(comparator);
        }
    }

    private Page<ProductDTO> paginate(List<ProductDTO> products, int page, int size) {
        int fromIndex = page * size;
        int toIndex = Math.min(fromIndex + size, products.size());

        if (fromIndex > products.size()) {
            return new PageImpl<>(Collections.emptyList(), PageRequest.of(page, size), products.size());
        }

        List<ProductDTO> pageContent = products.subList(fromIndex, toIndex);
        return new PageImpl<>(pageContent, PageRequest.of(page, size), products.size());
    }

}
