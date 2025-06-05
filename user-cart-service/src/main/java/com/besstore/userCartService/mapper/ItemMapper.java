package com.besstore.userCartService.mapper;

import com.besstore.userCartService.model.item.Item;
import com.besstore.userCartService.utils.FieldAdapter;
import com.common.lib.cartModule.itemDto.ItemDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = FieldAdapter.class)
public interface ItemMapper extends BaseMapper<Item, ItemDto> {


    @Override
    @Mapping(target = "priceSnapshot", source = "priceSnapshot", qualifiedByName = "priceAdapter")
    Item toEntity(ItemDto itemDto);


}
