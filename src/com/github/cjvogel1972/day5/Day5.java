package com.github.cjvogel1972.day5;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day5 {

    public static void main(String[] args) throws IOException {
//        List<HydrothermalVent> vents = readFile("input-files/day5.txt");
        List<HydrothermalVent> vents = testCase();

        checkCoordinates(vents, Day5::part1UseVent);
        checkCoordinates(vents, Day5::part2UseVent);
    }

    private static void checkCoordinates(List<HydrothermalVent> vents, Function<HydrothermalVent, Boolean> useVent) {
        Map<Coordinate, Integer> coordinateCount = new HashMap<>();
        for (HydrothermalVent vent : vents) {
            if (useVent.apply(vent)) {
                List<Coordinate> ventCoordinates = vent.getVentCoordinates();
                for (Coordinate coord : ventCoordinates) {
                    int ventCount = coordinateCount.getOrDefault(coord, 0);
                    ventCount++;
                    coordinateCount.put(coord, ventCount);
                }
            }
        }

        AtomicInteger multiVentCoordCount = new AtomicInteger();
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

    private static List<HydrothermalVent> readFile(String fileName) throws IOException {
        Path path = Paths.get(fileName);

        Stream<String> lines = Files.lines(path);
        List<HydrothermalVent> result = lines.map(Day5::parseLine)
                .collect(Collectors.toList());
        lines.close();

        return result;
    }

    private static List<HydrothermalVent> testCase() {
        List<String> coords = new ArrayList<>();
        coords.add("0,9 -> 5,9");
        coords.add("8,0 -> 0,8");
        coords.add("9,4 -> 3,4");
        coords.add("2,2 -> 2,1");
        coords.add("7,0 -> 7,4");
        coords.add("6,4 -> 2,0");
        coords.add("0,9 -> 2,9");
        coords.add("3,4 -> 1,4");
        coords.add("0,0 -> 8,8");
        coords.add("5,5 -> 8,2");

        Stream<String> lines = coords.stream();
        List<HydrothermalVent> result = lines.map(Day5::parseLine)
                .collect(Collectors.toList());
        lines.close();

        return result;
    }

    private static HydrothermalVent parseLine(String line) {
        String[] coordinates = line.split(" -> ");

        Coordinate coordinate1 = parseCoordinate(coordinates[0]);
        Coordinate coordinate2 = parseCoordinate(coordinates[1]);

        return new HydrothermalVent(coordinate1, coordinate2);
    }

    private static Coordinate parseCoordinate(String coordStr) {
        String[] s = coordStr.split(",");

        int x = Integer.parseInt(s[0]);
        int y = Integer.parseInt(s[1]);

        return new Coordinate(x, y);
    }
}
