package com.beststore.Mapper;

public interface BaseMapper<F, T> {
    T map(F obj);
}
