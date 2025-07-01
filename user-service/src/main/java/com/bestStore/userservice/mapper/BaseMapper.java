package com.bestStore.userservice.mapper;

import java.util.List;

public interface BaseMapper<E, D> {

    E toEntity(D d);

    D toDto(E e);

    List<E> toEntityList(List<D> ds);

    List<D> toDtoList(List<E> es);

}
