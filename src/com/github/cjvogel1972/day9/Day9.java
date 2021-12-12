package com.github.cjvogel1972.day9;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Day9 {

    public static void main(String[] args) throws IOException {
        var data = readFile("input-files/day9.txt");

        part1(data);
        part2(data);
    }

    private static List<List<Integer>> readFile(String fileName) throws IOException {
        var path = Paths.get(fileName);

        var lines = Files.lines(path);
        var result = lines.map(Day9::parseLine)
                .toList();
        lines.close();

        return result;
    }

    private static List<Integer> parseLine(String line) {
        return line.chars()
                .mapToObj(c -> Integer.parseInt(String.valueOf((char) c)))
                .toList();
    }

    private static void part1(List<List<Integer>> data) {
        var lowPoints = new ArrayList<Integer>();

        for (var i = 0; i < data.size(); i++) {
            for (var j = 0; j < data.get(i)
                    .size(); j++) {
                var lowest = true;
                var spot = data.get(i)
                        .get(j);

                if (i > 0 && spot >= data.get(i - 1)
                        .get(j)) {
                    lowest = false;
                }
                if (j > 0 && spot >= data.get(i)
                        .get(j - 1)) {
                    lowest = false;
                }
                if (i < (data.size() - 1) && spot >= data.get(i + 1)
                        .get(j)) {
                    lowest = false;
                }
                if (j < (data.get(i)
                        .size() - 1) && spot >= data.get(i)
                        .get(j + 1)) {
                    lowest = false;
                }
                if (lowest) {
                    lowPoints.add(spot);
                }
            }
        }

        int risk = lowPoints.stream()
                .reduce(0, Integer::sum);
        risk += lowPoints.size();

        System.out.println("Total risk = " + risk);
    }

    private static void part2(List<List<Integer>> data) {
        var basins = new ArrayList<Integer>();
        var covered = new ArrayList<List<Boolean>>();
        for (var row : data) {
            var inner = new ArrayList<Boolean>();
            for (var j = 0; j < row.size(); j++) {
                inner.add(false);
            }
            covered.add(inner);
        }

        for (var i = 0; i < data.size(); i++) {
            for (var j = 0; j < data.get(i)
                    .size(); j++) {
                if (Boolean.FALSE.equals(covered.get(i)
                        .get(j))) {
                    var size = findSizeOfBasin(data, covered, i, j);
                    if (size != 0) {
                        basins.add(size);
                    }
                }
            }
        }

        Collections.sort(basins);
        var total = 1;
        var size = basins.size();
        for (var i = (size - 1); i > (size - 4); i--) {
            total *= basins.get(i);
        }
        System.out.println("Three basins = " + total);
    }

    private static int findSizeOfBasin(List<List<Integer>> data, List<List<Boolean>> covered, int x, int y) {
        var size = 0;

        if (Boolean.FALSE.equals(covered.get(x)
                .get(y))) {
            covered.get(x)
                    .set(y, true);
            if (data.get(x)
                    .get(y) != 9) {
                size++;
                if (x > 0) {
                    size += findSizeOfBasin(data, covered, (x - 1), y);
                }
                if (x < (data.size() - 1)) {
                    size += findSizeOfBasin(data, covered, (x + 1), y);
                }
                if (y > 0) {
                    size += findSizeOfBasin(data, covered, x, (y - 1));
                }
                if (y < (data.get(x)
                        .size() - 1)) {
                    size += findSizeOfBasin(data, covered, x, (y + 1));
                }
            }
        }

        return size;
    }
}
