package com.github.cjvogel1972.day5;

import java.util.ArrayList;
import java.util.List;

public class HydrothermalVent {
    private Coordinate coord1;
    private Coordinate coord2;

    public HydrothermalVent(Coordinate coord1, Coordinate coord2) {
        this.coord1 = coord1;
        this.coord2 = coord2;
    }

    public boolean isHorizontal() {
        return (coord1.getY() == coord2.getY());
    }

    public boolean isVertical() {
        return (coord1.getX() == coord2.getX());
    }

    public List<Coordinate> getVentCoordinates() {
        List<Coordinate> result = new ArrayList<>();
        if (isHorizontal()) {
            result = computeHorizontalCoordinates();
        } else if (isVertical()) {
            result = computeVerticalCoordinates();
        } else {
            result = computeDiagonalCoordinates();
        }

        return result;
    }

    private List<Coordinate> computeHorizontalCoordinates() {
        List<Coordinate> result = new ArrayList<>();

        int y = coord1.getY();
        int xDelta = coord1.getX() < coord2.getX() ? 1 : -1;
        for (int x = coord1.getX(); x <= coord2.getX(); x = x + xDelta) {
            result.add(new Coordinate(x, y));
        }

        return result;
    }

    private List<Coordinate> computeVerticalCoordinates() {
        List<Coordinate> result = new ArrayList<>();

        int x = coord1.getX();
        int yDelta = coord1.getY() < coord2.getY() ? 1 : -1;
        for (int y = coord1.getY(); y <= coord2.getY(); y = y + yDelta) {
            result.add(new Coordinate(x, y));
        }

        return result;
    }

    private List<Coordinate> computeDiagonalCoordinates() {
        List<Coordinate> result = new ArrayList<>();

        int xDelta = coord1.getX() < coord2.getX() ? 1 : -1;
        int yDelta = coord1.getY() < coord2.getY() ? 1 : -1;
        result.add(coord1);
        Coordinate prevCoord = coord1;
        do {
            Coordinate newCoord = new Coordinate(prevCoord.getX() + xDelta, prevCoord.getY() + yDelta);
            result.add(newCoord);
            prevCoord = newCoord;
        } while (!prevCoord.equals(coord2));

        return result;
    }
}
