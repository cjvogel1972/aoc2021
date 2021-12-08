package com.github.cjvogel1972.day1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day1 {

    public static void main(String[] args) throws IOException {
        List<Integer> depths = readDepthFile("input-files/day1.txt");

        Sonar sonar = new Sonar(depths);
        int numberOfIncrements = sonar.calculateNumberOfIncrements();
        System.out.println("Number of increments = " + numberOfIncrements);

        SlidingWindowSonar slidingWindowSonar = new SlidingWindowSonar(depths);
        int slidingWindowIncrements = slidingWindowSonar.calculateNumberOfIncrements();
        System.out.println("Number of sliding window increments = " + slidingWindowIncrements);
    }

    private static List<Integer> readDepthFile(String fileName) throws IOException {
        Path path = Paths.get(fileName);

        Stream<String> lines = Files.lines(path);
        List<Integer> depths = lines
                .map(Integer::parseInt)
                .collect(Collectors.toList());
        lines.close();

        return depths;
    }
}
