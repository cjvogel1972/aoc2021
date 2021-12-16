package com.github.cjvogel1972.day15;

import com.github.cjvogel1972.util.Coordinate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.github.cjvogel1972.util.Utilities.charToInt;
import static com.github.cjvogel1972.util.Utilities.readFileLines;

public class Day15 {

    public static void main(String[] args) throws IOException {
        var data = readFileLines("input-files/day15.txt");
        var grid = parseLines(data);

        part1(grid);
        part2(grid);
    }

    private static Map<Coordinate, Integer> parseLines(List<String> lines) {
        var result = new HashMap<Coordinate, Integer>();

        for (var y = 0; y < lines.size(); y++) {
            String line = lines.get(y);
            for (var x = 0; x < line.length(); x++) {
                int weight = charToInt(line.charAt(x));
                result.put(new Coordinate(x, y), weight);
            }
        }

        return result;
    }

    private static void part1(Map<Coordinate, Integer> grid) {
        var endCoordinate = new Coordinate(computeMaxXCoordinate(grid), computeMaxYCoordinate(grid));
        System.out.println("Cost = " + dijkstra(grid, endCoordinate));
    }

    private static void part2(Map<Coordinate, Integer> grid) {
        var expandedGrid = expandGrid(grid);
        var endCoordinate = new Coordinate(computeMaxXCoordinate(expandedGrid), computeMaxYCoordinate(expandedGrid));
        System.out.println("Cost = " + dijkstra(expandedGrid, endCoordinate));
    }

    private static Map<Coordinate, Integer> expandGrid(Map<Coordinate, Integer> grid) {
        int maxX = computeMaxXCoordinate(grid) + 1;
        int maxY = computeMaxYCoordinate(grid) + 1;

        var expandedGrid = new HashMap<Coordinate, Integer>();
        for (Map.Entry<Coordinate, Integer> entry : grid.entrySet()) {
            var currCoord = entry.getKey();
            var prevRowStartValue = entry.getValue() - 1;
            for (var i = 0; i < 5; i++) {
                var weight = prevRowStartValue;
                for (var j = 0; j < 5; j++) {
                    var x = currCoord.x() + (maxX * j);
                    var y = currCoord.y() + (maxY * i);
                    weight++;
                    if (weight > 9) {
                        weight = 1;
                    }
                    if (j == 0) {
                        prevRowStartValue = weight;
                    }
                    var newCoord = new Coordinate(x, y);
                    expandedGrid.put(newCoord, weight);
                }
            }
        }

        return expandedGrid;
    }

    private static int computeMaxXCoordinate(Map<Coordinate, Integer> grid) {
        return grid.keySet()
                .stream()
                .mapToInt(Coordinate::x)
                .max()
                .getAsInt();
    }

    private static int computeMaxYCoordinate(Map<Coordinate, Integer> grid) {
        return grid.keySet()
                .stream()
                .mapToInt(Coordinate::y)
                .max()
                .getAsInt();
    }

    private static int dijkstra(Map<Coordinate, Integer> map, Coordinate endCoordinate) {
        var visited = new HashSet<Coordinate>();

        var distance = map.keySet()
                .stream()
                .collect(Collectors.toMap(Function.identity(), coordinate -> Integer.MAX_VALUE));
        var queue = new PriorityQueue<Path>();

        var start = new Coordinate(0, 0);
        queue.add(new Path(start, 0));
        distance.put(start, 0);
        visited.add(start);

        while (!queue.isEmpty()) {
            var shortest = queue.poll();
            var adjacent = computeAdjacents(shortest.coordinate());

            adjacent.stream()
                    .filter(map::containsKey)
                    .filter(coord -> !visited.contains(coord))
                    .map(coord -> new Path(coord, shortest.cost() + map.get(coord)))
                    .forEach(p -> {
                        distance.put(p.coordinate(), p.cost());
                        visited.add(p.coordinate());
                        queue.add(p);
                    });
        }

        return distance.get(endCoordinate);
    }

    private static ArrayList<Coordinate> computeAdjacents(Coordinate currentCoordinate) {
        var adjacent = new ArrayList<Coordinate>();
        adjacent.add(new Coordinate((currentCoordinate.x() - 1), currentCoordinate.y()));
        adjacent.add(new Coordinate(currentCoordinate.x(), (currentCoordinate.y() - 1)));
        adjacent.add(new Coordinate((currentCoordinate.x() + 1), currentCoordinate.y()));
        adjacent.add(new Coordinate(currentCoordinate.x(), (currentCoordinate.y() + 1)));
        return adjacent;
    }
}
