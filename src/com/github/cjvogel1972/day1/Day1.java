package com.github.cjvogel1972.day1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Day1 {

    public static void main(String[] args) throws IOException {
        var depths = readFile("input-files/day1.txt");

        part1(depths);
        part2(depths);
    }

    private static List<Integer> readFile(String fileName) throws IOException {
        var path = Paths.get(fileName);

        var lines = Files.lines(path);
        var depths = lines
                .map(Integer::parseInt)
                .toList();
        lines.close();

        return depths;
    }

    private static void part2(List<Integer> depths) {
        SlidingWindowSonar slidingWindowSonar = new SlidingWindowSonar(depths);
        int slidingWindowIncrements = slidingWindowSonar.calculateNumberOfIncrements();
        System.out.println("Number of sliding window increments = " + slidingWindowIncrements);
    }

    private static void part1(List<Integer> depths) {
        Sonar sonar = new Sonar(depths);
        int numberOfIncrements = sonar.calculateNumberOfIncrements();
        System.out.println("Number of increments = " + numberOfIncrements);
    }
}
