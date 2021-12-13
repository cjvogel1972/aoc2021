package com.github.cjvogel1972.day12;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.github.cjvogel1972.util.Utilities.readFileLines;

public class Day12 {

    public static void main(String[] args) throws IOException {
        var caves = readFile("input-files/day12.txt");

        part1(caves);
        part2(caves);
    }

    private static void part1(Map<String, Cave> caves) {
        var start = caves.get("start");
        var end = caves.get("end");

        var paths = traverseCaves(start, end, caves);
        System.out.println("Paths through the cave = " + paths.size());
    }

    private static Set<List<Cave>> traverseCaves(Cave start, Cave end, Map<String, Cave> caves) {
        var result = new HashSet<List<Cave>>();
        var path = new ArrayDeque<Cave>();
        var visited = new HashMap<Cave, Boolean>();
        for (var cave : caves.values()) {
            visited.put(cave, false);
        }
        path.add(start);

        traverseAllPaths(start, end, visited, path, result);

        return result;
    }

    private static void traverseAllPaths(Cave start, Cave end, Map<Cave, Boolean> visited, Deque<Cave> path,
                                         Set<List<Cave>> result) {
        if (start == end) {
            var copy = new ArrayList<Cave>(path);
            result.add(copy);
            return;
        }

        visited.put(start, true);
        for (var cave : start.getConnectedCaves()) {
            if (cave.isBigCave() || !visited.get(cave)) {
                path.addLast(cave);
                traverseAllPaths(cave, end, visited, path, result);
                path.removeLast();
            }
        }
        visited.put(start, false);
    }

    private static void part2(Map<String, Cave> caves) {
        var start = caves.get("start");
        var end = caves.get("end");

        var paths = traverseSmallCavesTwice(start, end, caves);
        System.out.println("Paths through the cave = " + paths.size());
    }

    private static Set<List<Cave>> traverseSmallCavesTwice(Cave start, Cave end, Map<String, Cave> caves) {
        var result = new LinkedHashSet<List<Cave>>();
        var path = new ArrayDeque<Cave>();
        var visited = new HashMap<Cave, Integer>();
        for (var cave : caves.values()) {
            visited.put(cave, 0);
        }
        path.add(start);

        var smallCaves = caves.values()
                .stream()
                .filter(Cave::isSmallCave)
                .filter(cave -> !cave.getName()
                        .equals("start"))
                .filter(cave -> !cave.getName()
                        .equals("end"))
                .toList();

        for (var smallCave : smallCaves) {
            traverseAllPathsSmallCavesTwice(start, end, smallCave, visited, path, result);
        }

        return result;
    }

    private static void traverseAllPathsSmallCavesTwice(Cave start, Cave end, Cave smallCave,
                                                        Map<Cave, Integer> visited,
                                                        Deque<Cave> path, Set<List<Cave>> result) {
        if (start == end) {
            var copy = new ArrayList<Cave>(path);
            result.add(copy);
            return;
        }

        var startVisitCount = visited.get(start);
        startVisitCount++;
        visited.put(start, startVisitCount);
        for (var cave : start.getConnectedCaves()) {
            if (cave.isBigCave() || (cave == smallCave && visited.get(cave) < 2) || (visited.get(cave) < 1)) {
                path.addLast(cave);
                traverseAllPathsSmallCavesTwice(cave, end, smallCave, visited, path, result);
                path.removeLast();
            }
        }
        startVisitCount--;
        visited.put(start, startVisitCount);
    }

    private static Map<String, Cave> readFile(String fileName) throws IOException {
        var lines = readFileLines(fileName);

        var caves = new HashMap<String, Cave>();
        for (var line : lines) {
            parseLine(line, caves);
        }
        return caves;
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
}
