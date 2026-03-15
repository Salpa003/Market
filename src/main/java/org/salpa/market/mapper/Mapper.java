package org.salpa.market.mapper;

public interface Mapper<F,T> {
    T map(F f);
}
