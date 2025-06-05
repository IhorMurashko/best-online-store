package com.besstore.userCartService.mapper;

import java.util.Set;

public interface BaseMapper<E, D> {

    E toEntity(D d);

    D toDto(E e);

    Set<E> toEntitySet(Set<D> ds);

    Set<D> toDtoSet(Set<E> es);
}