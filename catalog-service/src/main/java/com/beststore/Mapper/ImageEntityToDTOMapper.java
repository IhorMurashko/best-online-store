package com.beststore.Mapper;

import com.beststore.DTO.ImageDTO;
import com.beststore.Entity.Image;
import org.springframework.stereotype.Component;

@Component
public class ImageEntityToDTOMapper implements BaseMapper<Image, ImageDTO>{
    @Override
    public ImageDTO map(Image obj) {
        return ImageDTO.builder()
                .url(obj.getUrl())
                .productId(obj.getProduct().getId())
                .build();
    }
}
