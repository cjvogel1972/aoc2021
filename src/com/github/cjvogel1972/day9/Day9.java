package com.github.cjvogel1972.day9;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day9 {

    public static void main(String[] args) throws IOException {
        List<List<Integer>> data = readFile("input-files/day9.txt");

        part1(data);
        part2(data);
    }

    private static List<List<Integer>> readFile(String fileName) throws IOException {
        Path path = Paths.get(fileName);

        Stream<String> lines = Files.lines(path);
        List<List<Integer>> result = lines.map(line -> parseLine(line))
                .collect(Collectors.toList());
        lines.close();

        return result;
    }

    private static List<Integer> parseLine(String line) {
        return line.chars()
                .mapToObj(c -> Integer.parseInt(new String(new char[]{(char) c})))
                .collect(Collectors.toList());

    }

    private static void part1(List<List<Integer>> data) {
        List<Integer> lowPoints = new ArrayList<>();

        for (int i = 0; i < data.size(); i++) {
            for (int j = 0; j < data.get(i)
                    .size(); j++) {
                boolean lowest = true;
                int spot = data.get(i)
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
        List<Integer> basins = new ArrayList<>();
        List<List<Boolean>> covered = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            List<Boolean> inner = new ArrayList<>();
            for (int j = 0; j < data.get(i)
                    .size(); j++) {
                inner.add(false);
            }
            covered.add(inner);
        }

        for (int i = 0; i < data.size(); i++) {
            for (int j = 0; j < data.get(i)
                    .size(); j++) {
                if (!covered.get(i)
                        .get(j)) {
                    int size = findSizeOfBasin(data, covered, i, j);
                    if (size != 0) {
                        basins.add(size);
                    }
                }
            }
        }

        Collections.sort(basins);
        int total = 1;
        int size = basins.size();
        for (int i = (size - 1); i > (size - 4); i--) {
            System.out.println(basins.get(i));
            total *= basins.get(i);
        }
        System.out.println("Three basinss = " + total);
    }

    private static int findSizeOfBasin(List<List<Integer>> data, List<List<Boolean>> covered, int x, int y) {
        int size = 0;

        if (!covered.get(x)
                .get(y)) {
            covered.get(x)
                    .set(y, true);
            if (data.get(x)
                    .get(y) != 9) {
                size++;
                if (x > 0) {
                    size += findSizeOfBasin(data, covered, (x - 1), y);
                }
                if (x < (data.size() -1)) {
                    size += findSizeOfBasin(data, covered, (x + 1), y);
                }
                if (y > 0) {
                    size += findSizeOfBasin(data, covered, x, (y - 1));
                }
                if (y < (data.get(x)
                        .size() -1)) {
                    size += findSizeOfBasin(data, covered, x, (y + 1));
                }
            }
        }

        return size;
    }
}
