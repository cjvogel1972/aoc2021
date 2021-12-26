package com.github.cjvogel1972.day18;

public class SnailfishNumber implements SnailfishElement {
    private int number;

    public SnailfishNumber(int num) {
        number = num;
    }

    public int getNumber() {
        return number;
    }

    @Override
    public SnailfishElement add(SnailfishElement term) {
        if (term instanceof SnailfishNumber num) {
            number += num.number;
        }
        return null;
    }

    @Override
    public boolean reduce() {
        return false;
    }

    @Override
    public int magnitude() {
        return number;
    }

    @Override
    public SnailfishElement copy() {
        return new SnailfishNumber(number);
    }

    @Override
    public String toString() {
        return "" + number;
    }
}
