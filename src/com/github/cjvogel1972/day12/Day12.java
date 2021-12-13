package com.github.cjvogel1972.day12;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import static com.github.cjvogel1972.util.Utilities.readFileLines;

public class Day12 {

    public static void main(String[] args) throws IOException {
        var caves = readFile("input-files/day12.txt");
        var start = findCave(caves, "start");
        var end = findCave(caves, "end");

        part1(caves, start, end);
        part2(caves, start, end);
    }

    private static List<Cave> readFile(String fileName) throws IOException {
        var lines = readFileLines(fileName);

        var caves = new HashMap<String, Cave>();
        for (var line : lines) {
            parseLine(line, caves);
        }

        return new ArrayList<>(caves.values());
    }

    private static void parseLine(String line, Map<String, Cave> caves) {
        var names = line.split("-");

        var cave1 = caves.get(names[0]);
        if (cave1 == null) {
            cave1 = new Cave(names[0]);
            caves.put(names[0], cave1);
        }

        var cave2 = caves.get(names[1]);
        if (cave2 == null) {
            cave2 = new Cave(names[1]);
            caves.put(names[1], cave2);
        }

        cave1.addConnectedCave(cave2);
        cave2.addConnectedCave(cave1);
    }

    private static Cave findCave(List<Cave> caves, String name) {
        return caves.stream()
                .filter(cave -> cave.getName()
                        .equals(name))
                .findFirst()
                .get();
    }

    private static void part1(List<Cave> caves, Cave start, Cave end) {
        var tracker = new SmallCaveOnceTracker(caves);
        var cavePaths = new CavePaths(start, end, tracker);
        cavePaths.traverseCaves();

        System.out.println("Paths through the cave = " + cavePaths.getPaths()
                .size());
    }

    private static void part2(List<Cave> caves, Cave start, Cave end) {
        var smallCaves = filterSmallCaves(caves);
        var allPaths = new HashSet<List<Cave>>();

        for (var smallCave : smallCaves) {
            var tracker = new SmallCaveTwiceTracker(caves, smallCave);
            var cavePaths = new CavePaths(start, end, tracker);
            cavePaths.traverseCaves();
            allPaths.addAll(cavePaths.getPaths());
        }

        System.out.println("Paths through the cave = " + allPaths.size());
    }

    private static List<Cave> filterSmallCaves(List<Cave> caves) {
        return caves.stream()
                .filter(Cave::isSmallCave)
                .filter(cave -> !cave.getName()
                        .equals("start"))
                .filter(cave -> !cave.getName()
                        .equals("end"))
                .toList();
    }
}
