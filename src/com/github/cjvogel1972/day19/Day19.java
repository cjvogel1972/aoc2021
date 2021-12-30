package com.github.cjvogel1972.day19;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.function.UnaryOperator;

import static com.github.cjvogel1972.util.Utilities.readFileLines;

public class Day19 {

    static final List<UnaryOperator<Coordinate3D>> rotations = new ArrayList<>();

    static {
        rotations.add(beacon -> beacon);
        rotations.add(Coordinate3D::rotateZAxis);
        rotations.add(beacon -> beacon.rotateZAxis()
                .rotateZAxis());
        rotations.add(beacon -> beacon.rotateZAxis()
                .rotateZAxis()
                .rotateZAxis());
        rotations.add(Coordinate3D::rotateYAxis);
        rotations.add(beacon -> beacon.rotateYAxis()
                .rotateZAxis());
        rotations.add(beacon -> beacon.rotateYAxis()
                .rotateZAxis()
                .rotateZAxis());
        rotations.add(beacon -> beacon.rotateYAxis()
                .rotateZAxis()
                .rotateZAxis()
                .rotateZAxis());
        rotations.add(beacon -> beacon.rotateYAxis()
                .rotateYAxis());
        rotations.add(beacon -> beacon.rotateYAxis()
                .rotateYAxis()
                .rotateZAxis());
        rotations.add(beacon -> beacon.rotateYAxis()
                .rotateYAxis()
                .rotateZAxis()
                .rotateZAxis());
        rotations.add(beacon -> beacon.rotateYAxis()
                .rotateYAxis()
                .rotateZAxis()
                .rotateZAxis()
                .rotateZAxis());
        rotations.add(beacon -> beacon.rotateYAxis()
                .rotateYAxis()
                .rotateYAxis());
        rotations.add(beacon -> beacon.rotateYAxis()
                .rotateYAxis()
                .rotateYAxis()
                .rotateZAxis());
        rotations.add(beacon -> beacon.rotateYAxis()
                .rotateYAxis()
                .rotateYAxis()
                .rotateZAxis()
                .rotateZAxis());
        rotations.add(beacon -> beacon.rotateYAxis()
                .rotateYAxis()
                .rotateYAxis()
                .rotateZAxis()
                .rotateZAxis()
                .rotateZAxis());
        rotations.add(Coordinate3D::rotateXAxis);
        rotations.add(beacon -> beacon.rotateXAxis()
                .rotateYAxis()
                .rotateYAxis());
        rotations.add(beacon -> beacon.rotateXAxis()
                .rotateYAxis()
                .rotateYAxis()
                .rotateZAxis());
        rotations.add(beacon -> beacon.rotateXAxis()
                .rotateYAxis()
                .rotateYAxis()
                .rotateZAxis()
                .rotateZAxis());
        rotations.add(beacon -> beacon.rotateXAxis()
                .rotateYAxis()
                .rotateYAxis()
                .rotateZAxis()
                .rotateZAxis()
                .rotateZAxis());
        rotations.add(beacon -> beacon.rotateXAxis()
                .rotateXAxis()
                .rotateXAxis()
                .rotateYAxis()
                .rotateYAxis());
        rotations.add(beacon -> beacon.rotateXAxis()
                .rotateXAxis()
                .rotateXAxis()
                .rotateYAxis()
                .rotateYAxis()
                .rotateZAxis());
        rotations.add(beacon -> beacon.rotateXAxis()
                .rotateXAxis()
                .rotateXAxis()
                .rotateYAxis()
                .rotateYAxis()
                .rotateZAxis()
                .rotateZAxis()
                .rotateZAxis());
    }

    public static void main(String[] args) throws IOException {
        List<String> data = readFileLines("input-files/day19.txt");

        var scanners = part1(data);
        part2(scanners);
    }

    private static List<Scanner> parseData(List<String> data) {
        var scanners = new ArrayList<Scanner>();
        Scanner currentScanner = null;

        for (var line : data) {
            if (line.contains("scanner")) {
                var s = line.split(" ");
                var scannerId = Integer.parseInt(s[2]);
                currentScanner = new Scanner(scannerId);
            } else if (line.isBlank()) {
                scanners.add(currentScanner);
                currentScanner = null;
            } else {
                currentScanner.addBeacon(parseLine(line));
            }
        }
        if (currentScanner != null) {
            scanners.add(currentScanner);
        }

        scanners.get(0)
                .setCoordinate(new Coordinate3D(0, 0, 0), rotations.get(0));

        return scanners;
    }

    static Coordinate3D parseLine(String line) {
        var s = line.split(",");

        var x = Integer.parseInt(s[0]);
        var y = Integer.parseInt(s[1]);
        var z = Integer.parseInt(s[2]);

        return new Coordinate3D(x, y, z);
    }

    private static List<Scanner> part1(List<String> data) {
        var scanners = parseData(data);

        var allSet = false;
        do {
            for (var scanner1 : scanners) {
                if (scanner1.isCoordinateSet()) {
                    for (var scanner2 : scanners) {
                        if (scanner1.equals(scanner2) || scanner2.isCoordinateSet()) {
                            continue;
                        }

                        checkScanners(scanner1, scanner2);
                    }
                }

                allSet = scanners.stream()
                        .allMatch(Scanner::isCoordinateSet);
            }
        } while (!allSet);

        var beacons = new HashSet<Coordinate3D>();
        for (Scanner scanner : scanners) {
            beacons.addAll(scanner.getBeacons());
        }

        System.out.println("There are " + beacons.size() + " beacons.");

        return scanners;
    }

    static void checkScanners(Scanner scanner1, Scanner scanner2) {
        for (UnaryOperator<Coordinate3D> rotation : rotations) {
            var possibleCoordinates = new HashMap<Coordinate3D, Integer>();
            for (Coordinate3D s1Beacon : scanner1.getBeacons()) {
                for (Coordinate3D s4Beacon : scanner2.getBeacons()) {
                    var rotatedCoord = rotation
                            .apply(s4Beacon);
                    possibleCoordinates.compute(s1Beacon.subtract(rotatedCoord),
                                                (k, v) -> (v == null) ? 1 : v + 1);
                }
            }

            for (var entry : possibleCoordinates.entrySet()) {
                if (entry.getValue() >= 12) {
                    scanner2.setCoordinate(entry.getKey(), rotation);
                }
            }

            if (scanner2.isCoordinateSet()) {
                break;
            }
        }
    }

    private static void part2(List<Scanner> scanners) {
        var maxDistance = 0;

        for (var i = 0; i < scanners.size() - 1; i++) {
            var scanner1Coord = scanners.get(i)
                    .getCoordinate();
            for (var j = i + 1; j < scanners.size(); j++) {
                var distance = scanner1Coord.manhattanDistance(scanners.get(j)
                                                                       .getCoordinate());
                if (distance > maxDistance) {
                    maxDistance = distance;
                }
            }
        }

        System.out.println("Max distance = " + maxDistance);
    }
}
