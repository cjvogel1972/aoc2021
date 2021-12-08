package com.github.cjvogel1972.day7;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Day7 {

    public static void main(String[] args) throws IOException {
        List<Integer> data = readFile("input-files/day7.txt");
        computeLeastTotalFuelUsed(data, Day7::part1ComputeHorizontalPosition, Day7::part1CrabFuelUsed);
        computeLeastTotalFuelUsed(data, Day7::part2ComputeHorizontalPosition, Day7::part2CrabFuelUsed);
    }

    private static void computeLeastTotalFuelUsed(List<Integer> data,
                                                  Function<List<Integer>, Integer> computeHorizontalPosition,
                                                  Function<Integer, Integer> computeFuelUsed) {
        int horizontalPosition = computeHorizontalPosition.apply(data);

        int fuelUsed = IntStream.rangeClosed(horizontalPosition - 1, horizontalPosition + 1)
                .map(pos -> computeTotalFuelUsed(data, computeFuelUsed, pos))
                .min()
                .getAsInt();

        System.out.println("Total fuel used = " + fuelUsed);
    }

    private static void computeLeastTotalFuelUsedBruteForce(List<Integer> data,
                                                  Function<Integer, Integer> computeFuelUsed) {
        int minLoc = data.stream()
                .mapToInt(v -> v)
                .min()
                .getAsInt();
        int maxLoc = data.stream()
                .mapToInt(v -> v)
                .max()
                .getAsInt();

        int fuelUsed = IntStream.rangeClosed(minLoc, maxLoc)
                .map(pos -> computeTotalFuelUsed(data, computeFuelUsed, pos))
                .min()
                .getAsInt();

        System.out.println("Total fuel used = " + fuelUsed);
    }

    private static int computeTotalFuelUsed(List<Integer> data, Function<Integer, Integer> computeFuelUsed,
                                            int horizontalPosition) {
        int fuelUsed = 0;
        for (Integer crabLoc : data) {
            int distance = Math.abs(crabLoc - horizontalPosition);
            int crabFuelUsed = computeFuelUsed.apply(distance);
            fuelUsed += crabFuelUsed;
        }

        return fuelUsed;
    }

    private static int part1ComputeHorizontalPosition(List<Integer> data) {
        Collections.sort(data);
        int median;

        if (data.size() % 2 == 0) {
            median = (data.get(data.size() / 2) + data.get(data.size() / 2 - 1)) / 2;
        } else {
            median = data.get(data.size() / 2);
        }

        return median;
    }

    private static int part1CrabFuelUsed(int distance) {
        return distance;
    }

    private static int part2ComputeHorizontalPosition(List<Integer> data) {
        int totalLoc = data.stream()
                .reduce(0, Integer::sum);
        double avg = (double) totalLoc / data.size();
        int avgLoc = (int) Math.round(avg);

        return avgLoc;
    }

    private static int part2CrabFuelUsed(int distance) {
        return IntStream.rangeClosed(1, distance)
                .reduce(0, Integer::sum);
    }

    private static List<Integer> readFile(String fileName) throws IOException {
        Path path = Paths.get(fileName);

        Stream<String> lines = Files.lines(path);
        List<String> result = lines.collect(Collectors.toList());
        lines.close();

        String[] nums = result.get(0)
                .split(",");

        return Arrays.stream(nums)
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }

    private static void junk(List<Integer> data) {
        int minLoc = data.stream()
                .mapToInt(v -> v)
                .min()
                .getAsInt();
        int maxLoc = data.stream()
                .mapToInt(v -> v)
                .max()
                .getAsInt();
        int totalLoc = data.stream()
                .reduce(0, Integer::sum);
        double avg = (double) totalLoc / data.size();
        System.out.println(avg);
        System.out.println(Math.round(avg));
        int avgLoc = (int) Math.round(avg);
        Collections.sort(data);
        int median;
        if (data.size() % 2 == 0) {
            median = (data.get(data.size() / 2) + data.get(data.size() / 2 - 1)) / 2;
        } else {
            median = data.get(data.size() / 2);
        }
        System.out.println(
                "Min = " + minLoc + " max = " + maxLoc + " average location = " + avgLoc + " median = " + median);


    }
}
