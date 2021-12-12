package com.github.cjvogel1972.day1;

import java.io.IOException;
import java.util.List;

import static com.github.cjvogel1972.util.Utilities.parseFile;

public class Day1 {

    public static void main(String[] args) throws IOException {
        var depths = readFile("input-files/day1.txt");

        part1(depths);
        part2(depths);
    }

    private static List<Integer> readFile(String fileName) throws IOException {
        return parseFile(fileName, Integer::parseInt);
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
