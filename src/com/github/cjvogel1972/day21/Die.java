package com.github.cjvogel1972.day21;

public class Die {
    private int sides;
    private int nextRoll;

    public Die(int sides) {
        this.sides = sides;
        nextRoll = 1;
    }

    public int roll() {
        if (nextRoll == (sides + 1)) {
            nextRoll = 1;
        }
        return nextRoll++;
    }
}
