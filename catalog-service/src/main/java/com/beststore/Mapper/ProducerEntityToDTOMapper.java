package com.beststore.Mapper;

import com.beststore.DTO.ProducerDTO;
import com.beststore.Entity.Producer;
import org.springframework.stereotype.Component;

@Component
public class ProducerEntityToDTOMapper implements BaseMapper<Producer, ProducerDTO> {
    @Override
    public ProducerDTO map(Producer obj) {
        return ProducerDTO.builder()
                .producerName(obj.getProducerName())
                .build();
    }
}
