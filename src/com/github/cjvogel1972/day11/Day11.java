package com.github.cjvogel1972.day11;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Day11 {

    public static void main(String[] args) throws IOException {
        part1();
        part2();
    }

    private static void part1() throws IOException {
        var data = readFile("input-files/day11.txt");
        initNeighbors(data);

        var flashes = 0;

        for (var step = 0; step < 100; step++) {
            incrementAllOctopi(data);

            flashes += countAndResetFlashes(data);
        }

        System.out.println("Octopus flashes = " + flashes);
    }

    private static void part2() throws IOException {
        var data = readFile("input-files/day11.txt");
        initNeighbors(data);

        var synched = false;
        var step = 0;
        while (!synched) {
            step++;
            incrementAllOctopi(data);

            var flashes = countAndResetFlashes(data);

            if (flashes == 100) {
                synched = true;
            }
        }

        System.out.println("Octopi synched on step " + step);
    }

    private static void incrementAllOctopi(List<List<Octopus>> data) {
        for (var row : data) {
            for (var octopus : row) {
                octopus.incrementEnergy();
            }
        }
    }

    private static int countAndResetFlashes(List<List<Octopus>> data) {
        var flashes = 0;

        for (var row : data) {
            for (var octopus : row) {
                if (octopus.flashedThisStep()) {
                    flashes++;
                    octopus.resetFlash();
                }
            }
        }

        return flashes;
    }

    private static List<List<Octopus>> readFile(String fileName) throws IOException {
        var path = Paths.get(fileName);

        var lines = Files.lines(path);
        var result = lines.map(Day11::parseLine)
                .toList();
        lines.close();

        return result;
    }

    private static List<Octopus> parseLine(String line) {
        return line.chars()
                .mapToObj(c -> new Octopus(Integer.parseInt(String.valueOf((char) c))))
                .toList();

    }

    private static void initNeighbors(List<List<Octopus>> data) {
        for (var i = 0; i < data.size(); i++) {
            for (var j = 0; j < data.get(i)
                    .size(); j++) {
                var octopus = data.get(i)
                        .get(j);
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
