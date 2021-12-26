package com.github.cjvogel1972.day18;

public interface SnailfishElement {
    SnailfishElement add(SnailfishElement term);

    boolean reduce();

    int magnitude();

    SnailfishElement copy();
}
