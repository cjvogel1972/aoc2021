package com.github.cjvogel1972.day17;

public record TargetArea(int minX, int maxX, int minY, int maxY) {

    public boolean withinX(int x) {
        return x >= minX && x <= maxX;
    }

    public boolean withinY(int y) {
        return y >= minY && y <= maxY;
    }
}
