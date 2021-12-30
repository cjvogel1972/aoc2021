package com.github.cjvogel1972.day19;

import org.junit.jupiter.api.Test;

import static com.github.cjvogel1972.day19.Day19.rotations;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ScannerTest {

    @Test
    void setCoordinate() {
        var scanner = new Scanner(0);
        scanner.addBeacon(new Coordinate3D(10, 10, 10));
        scanner.addBeacon(new Coordinate3D(-10, -10, -10));

        var newScannerCoordinate = new Coordinate3D(1, 1, 1);
        scanner.setCoordinate(newScannerCoordinate, rotations.get(0));

        assertEquals(new Coordinate3D(11, 11, 11), scanner.getBeacons().get(0));
        assertEquals(new Coordinate3D(-9, -9, -9), scanner.getBeacons().get(1));
    }

    @Test
    void setCoordinate2() {
        var scanner = new Scanner(0);
        scanner.addBeacon(new Coordinate3D(686, 422, 578));

        scanner.setCoordinate(new Coordinate3D(68, -1246, -43), rotations.get(2));
        assertTrue(scanner.getBeacons()
                           .contains(new Coordinate3D(-618, -824, -621)));
    }
}