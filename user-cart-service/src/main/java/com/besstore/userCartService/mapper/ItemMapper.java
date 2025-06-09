package com.besstore.userCartService.mapper;

import com.besstore.userCartService.model.item.Item;
import com.besstore.userCartService.utils.FieldAdapter;
import com.common.lib.cartModule.itemDto.ItemDto;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = FieldAdapter.class)
public interface ItemMapper extends BaseMapper<Item, ItemDto> {


    @Override
    @Mapping(target = "priceSnapshot", source = "priceSnapshot", qualifiedByName = "priceAdapter")
    Item toEntity(ItemDto itemDto);

    @Mapping(target = "priceSnapshot", source = "priceSnapshot", qualifiedByName = "priceAdapter")
    void updateEntityFromDto(ItemDto itemDto, @MappingTarget Item item);

}
