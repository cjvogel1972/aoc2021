package com.github.cjvogel1972.day11;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day11 {

    public static void main(String[] args) throws IOException {
        part1();
        part2();
    }

    private static void part1() throws IOException {
        List<List<Octopus>> data = readFile("input-files/day11.txt");
        initNeighbors(data);

        int flashes = 0;

        for (int step = 0; step < 100; step++) {
            for (List<Octopus> row : data) {
                for (Octopus octopus : row) {
                    octopus.incrementEnergy();
                }
            }

            flashes += countAndResetFlashes(data);
        }

        System.out.println("Octopus flashes = " + flashes);
    }

    private static void part2() throws IOException {
        List<List<Octopus>> data = readFile("input-files/day11.txt");
        initNeighbors(data);

        boolean synched = false;
        int step = 0;
        while (!synched) {
            step++;
            for (List<Octopus> row : data) {
                for (Octopus octopus : row) {
                    octopus.incrementEnergy();
                }
            }

            int flashes = countAndResetFlashes(data);

            if (flashes == 100) {
                synched = true;
            }
        }

        System.out.println("Octopi synched on step " + step);
    }

    private static int countAndResetFlashes(List<List<Octopus>> data) {
        int flashes = 0;

        for (List<Octopus> row : data) {
            for (Octopus octopus : row) {
                if (octopus.flashedThisStep()) {
                    flashes++;
                    octopus.resetFlash();
                }
            }
        }

        return flashes;
    }

    private static List<List<Octopus>> readFile(String fileName) throws IOException {
        Path path = Paths.get(fileName);

        Stream<String> lines = Files.lines(path);
        List<List<Octopus>> result = lines.map(Day11::parseLine)
                .collect(Collectors.toList());
        lines.close();

        return result;
    }

    private static List<Octopus> parseLine(String line) {
        return line.chars()
                .mapToObj(c -> new Octopus(Integer.parseInt(String.valueOf((char) c))))
                .collect(Collectors.toList());

    }

    private static void initNeighbors(List<List<Octopus>> data) {
        for (int i = 0; i < data.size(); i++) {
            for (int j = 0; j < data.get(i).size(); j++) {
                Octopus octopus = data.get(i).get(j);
                if (i > 0) {
                    addNeighborsFromRow(octopus, data.get(i - 1), j);
                }
                addNeighborsFromRow(octopus, data.get(i), j);
                if (i < (data.size() - 1)) {
                    addNeighborsFromRow(octopus, data.get(i + 1), j);
                }
            }
        }
    }

    private static void addNeighborsFromRow(Octopus octopus, List<Octopus> row, int octopusIndex) {
        if (octopusIndex > 0) {
            octopus.addNeighbor(row.get(octopusIndex - 1));
        }
        octopus.addNeighbor(row.get(octopusIndex));
        if (octopusIndex < (row.size() - 1)) {
            octopus.addNeighbor(row.get(octopusIndex + 1));
        }
    }
}
