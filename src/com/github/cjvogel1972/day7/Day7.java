package com.github.cjvogel1972.day7;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.function.ToIntFunction;
import java.util.stream.IntStream;

import static com.github.cjvogel1972.util.Utilities.readFileLines;

public class Day7 {

    public static void main(String[] args) throws IOException {
        var data = readFile("input-files/day7.txt");
        computeLeastTotalFuelUsed(data, Day7::part1ComputeHorizontalPosition, Day7::part1CrabFuelUsed);
        computeLeastTotalFuelUsedBruteForce(data, Day7::part1CrabFuelUsed);
        computeLeastTotalFuelUsed(data, Day7::part2ComputeHorizontalPosition, Day7::part2CrabFuelUsed);
        computeLeastTotalFuelUsedBruteForce(data, Day7::part2CrabFuelUsed);
    }

    private static void computeLeastTotalFuelUsed(List<Integer> data,
                                                  ToIntFunction<List<Integer>> computeHorizontalPosition,
                                                  ToIntFunction<Integer> computeFuelUsed) {
        var horizontalPosition = computeHorizontalPosition.applyAsInt(data);

        var fuelUsed = IntStream.rangeClosed(horizontalPosition - 1, horizontalPosition + 1)
                .map(pos -> computeTotalFuelUsed(data, computeFuelUsed, pos))
                .min()
                .getAsInt();

        System.out.println("Total fuel used = " + fuelUsed);
    }

    private static void computeLeastTotalFuelUsedBruteForce(List<Integer> data,
                                                            ToIntFunction<Integer> computeFuelUsed) {
        var minLoc = data.stream()
                .mapToInt(v -> v)
                .min()
                .getAsInt();
        var maxLoc = data.stream()
                .mapToInt(v -> v)
                .max()
                .getAsInt();

        var fuelUsed = IntStream.rangeClosed(minLoc, maxLoc)
                .map(pos -> computeTotalFuelUsed(data, computeFuelUsed, pos))
                .min()
                .getAsInt();

        System.out.println("Total fuel used = " + fuelUsed);
    }

    private static int computeTotalFuelUsed(List<Integer> data, ToIntFunction<Integer> computeFuelUsed,
                                            int horizontalPosition) {
        var fuelUsed = 0;
        for (var crabLoc : data) {
            var distance = Math.abs(crabLoc - horizontalPosition);
            var crabFuelUsed = computeFuelUsed.applyAsInt(distance);
            fuelUsed += crabFuelUsed;
        }

        return fuelUsed;
    }

    private static int part1ComputeHorizontalPosition(List<Integer> data) {
        var median = 0;

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
        var totalLoc = data.stream()
                .reduce(0, Integer::sum);
        var avg = (double) totalLoc / data.size();

        return (int) Math.round(avg);
    }

    private static int part2CrabFuelUsed(int distance) {
        return IntStream.rangeClosed(1, distance)
                .reduce(0, Integer::sum);
    }

    private static List<Integer> readFile(String fileName) throws IOException {
        var result = readFileLines(fileName);

        var nums = result.get(0)
                .split(",");

        return Arrays.stream(nums)
                .map(Integer::parseInt)
                .sorted()
                .toList();
    }
}
