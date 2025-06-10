package com.besstore.userCartService.mapper;

import java.util.Set;

/**
 * Generic interface for mapping between entity objects and DTOs.
 *
 * @param <E> the entity type
 * @param <D> the DTO type
 */
public interface BaseMapper<E, D> {

    /**
     * Converts a DTO to an entity.
     *
     * @param d the DTO to convert
     * @return the corresponding entity
     */
    E toEntity(D d);

    /**
     * Converts an entity to a DTO.
     *
     * @param e the entity to convert
     * @return the corresponding DTO
     */
    D toDto(E e);

    /**
     * Converts a set of DTOs to a set of entities.
     *
     * @param ds the set of DTOs to convert
     * @return the corresponding set of entities
     */
    Set<E> toEntitySet(Set<D> ds);

    /**
     * Converts a set of entities to a set of DTOs.
     *
     * @param es the set of entities to convert
     * @return the corresponding set of DTOs
     */
    Set<D> toDtoSet(Set<E> es);
}
