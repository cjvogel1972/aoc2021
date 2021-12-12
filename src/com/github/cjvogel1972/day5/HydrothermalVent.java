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
        return computeCoordinates();
    }

    private List<Coordinate> computeCoordinates() {
        List<Coordinate> result = new ArrayList<>();

        int xDelta = coord2.getX() - coord1.getX();
        if (xDelta != 0) {
            xDelta = xDelta / Math.abs(xDelta);
        }
        int yDelta = coord2.getY() - coord1.getY();
        if (yDelta != 0) {
            yDelta = yDelta / Math.abs(yDelta);
        }

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
