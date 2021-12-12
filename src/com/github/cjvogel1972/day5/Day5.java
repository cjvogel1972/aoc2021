package com.github.cjvogel1972.day5;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;

public class Day5 {

    public static void main(String[] args) throws IOException {
        var vents = readFile("input-files/day5.txt");

        checkCoordinates(vents, Day5::part1UseVent);
        checkCoordinates(vents, Day5::part2UseVent);
    }

    private static List<HydrothermalVent> readFile(String fileName) throws IOException {
        var path = Paths.get(fileName);

        var lines = Files.lines(path);
        var result = lines.map(Day5::parseLine)
                .toList();
        lines.close();

        return result;
    }

    private static HydrothermalVent parseLine(String line) {
        var coordinates = line.split(" -> ");

        var coordinate1 = parseCoordinate(coordinates[0]);
        var coordinate2 = parseCoordinate(coordinates[1]);

        return new HydrothermalVent(coordinate1, coordinate2);
    }

    private static Coordinate parseCoordinate(String coordStr) {
        var s = coordStr.split(",");

        var x = Integer.parseInt(s[0]);
        var y = Integer.parseInt(s[1]);

        return new Coordinate(x, y);
    }

    private static void checkCoordinates(List<HydrothermalVent> vents, Predicate<HydrothermalVent> useVent) {
        var coordinateCount = new HashMap<Coordinate, Integer>();
        for (HydrothermalVent vent : vents) {
            if (useVent.test(vent)) {
                var ventCoordinates = vent.getVentCoordinates();
                for (Coordinate coord : ventCoordinates) {
                    var ventCount = coordinateCount.getOrDefault(coord, 0);
                    ventCount++;
                    coordinateCount.put(coord, ventCount);
                }
            }
        }

        var multiVentCoordCount = new AtomicInteger();
        coordinateCount.forEach((k, v) -> {
            if (v > 1) {
                multiVentCoordCount.getAndIncrement();
            }
        });

        System.out.println("Two lines overlap " + multiVentCoordCount.get() + " places");
    }

    private static boolean part1UseVent(HydrothermalVent vent) {
        return (vent.isHorizontal() || vent.isVertical());
    }

    private static boolean part2UseVent(HydrothermalVent vent) {
        return true;
    }
}
