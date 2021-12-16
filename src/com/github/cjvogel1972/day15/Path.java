package com.github.cjvogel1972.day15;

import com.github.cjvogel1972.util.Coordinate;

import java.util.Comparator;

public record Path(Coordinate coordinate, int cost) implements Comparable<Path> {
    private static final Comparator<Path> COMPARATOR = Comparator.comparingInt(Path::cost);

    @Override
    public int compareTo(Path p) {
        return COMPARATOR.compare(this, p);
    }
}
