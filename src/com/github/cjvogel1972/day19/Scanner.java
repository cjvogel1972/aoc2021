package com.github.cjvogel1972.day19;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.UnaryOperator;

public class Scanner {
    private final int id;
    private Coordinate3D coordinate;
    private boolean coordinateSet;
    private List<Coordinate3D> beacons;

    public Scanner(int id) {
        this.id = id;
        coordinate = new Coordinate3D(0, 0, 0);
        coordinateSet = false;
        beacons = new ArrayList<>();
    }

    public void addBeacon(Coordinate3D coordinate) {
        beacons.add(coordinate);
    }

    public List<Coordinate3D> getBeacons() {
        return beacons;
    }

    public boolean isCoordinateSet() {
        return coordinateSet;
    }

    public Coordinate3D getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate3D newCoordinate, UnaryOperator<Coordinate3D> rotation) {
        if (!this.coordinate.equals(newCoordinate)) {
            beacons = beacons.stream()
                    .map(beacon -> newCoordinate.add(rotation.apply(beacon)))
                    .toList();
            this.coordinate = newCoordinate;
        }

        coordinateSet = true;
    }

    @Override
    public String toString() {
        return "Scanner{" +
                "id=" + id +
                ", coordinate=" + coordinate +
                ", coordinateSet=" + coordinateSet +
                ", beacons=" + beacons +
                '}';
    }
}
