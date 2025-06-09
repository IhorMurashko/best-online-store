package com.besstore.userCartService.mapper;

import com.besstore.userCartService.model.item.Item;
import com.besstore.userCartService.utils.FieldAdapter;
import com.common.lib.cartModule.itemDto.ItemDto;
import org.mapstruct.*;

/**
 * MapStruct mapper for converting between Item entities and ItemDto DTOs.
 * Uses a custom field adapter for handling price conversions.
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = FieldAdapter.class)
public interface ItemMapper extends BaseMapper<Item, ItemDto> {

    /**
     * {@inheritDoc}
     * <p>
     * Uses a custom price adapter for the priceSnapshot field.
     */
    @Override
    @Mapping(target = "priceSnapshot", source = "priceSnapshot", qualifiedByName = "priceAdapter")
    Item toEntity(ItemDto itemDto);

    /**
     * Updates an existing Item entity with data from an ItemDto.
     * Uses a custom price adapter for the priceSnapshot field.
     *
     * @param itemDto the source DTO with updated data
     * @param item the target entity to update
     */
    @Mapping(target = "priceSnapshot", source = "priceSnapshot", qualifiedByName = "priceAdapter")
    void updateEntityFromDto(ItemDto itemDto, @MappingTarget Item item);

}
