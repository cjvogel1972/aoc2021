package com.github.cjvogel1972.day19;

public record Coordinate3D(int x, int y, int z) {
    public Coordinate3D add(Coordinate3D coord) {
        return new Coordinate3D(x + coord.x, y + coord.y, z + coord.z);
    }

    public Coordinate3D subtract(Coordinate3D coord) {
        return new Coordinate3D(x - coord.x, y - coord.y, z - coord.z);
    }

    public Coordinate3D rotateXAxis() {
        return new Coordinate3D(y, -x, z);
    }

    public Coordinate3D rotateYAxis() {
        return new Coordinate3D(x, -z, y);
    }

    public Coordinate3D rotateZAxis() {
        return new Coordinate3D(z, y, -x);
    }

    public int manhattanDistance(Coordinate3D coord) {
        return Math.abs(x - coord.x) + Math.abs(y - coord.y) + Math.abs(z - coord.z);
    }
}
