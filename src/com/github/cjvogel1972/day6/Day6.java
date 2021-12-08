package com.github.cjvogel1972.day6;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day6 {

    public static void main(String[] args) throws IOException {
        computeLanternfish(80);
        computeLanternfish(256);
    }

    private static void computeLanternfish(int numberOfDays) throws IOException {
        List<Integer> data = readFile("input-files/day6.txt");

        List<Long> fishPerTimer = initializeFishPerTimer(data);

        for (int day = 0; day < numberOfDays; day++) {
            long numberOfFishWithTimer0 = fishPerTimer.get(0);
            for (int i = 1; i < 9; i++) {
                fishPerTimer.set((i - 1), fishPerTimer.get(i));
            }
            fishPerTimer.set(8, numberOfFishWithTimer0);
            fishPerTimer.set(6, (fishPerTimer.get(6) + numberOfFishWithTimer0));
        }

        long totalNumberOfFish = computeTotalNumberOfFish(fishPerTimer);
        System.out.println("After " + numberOfDays + " days, there are " + totalNumberOfFish + " lanternfish");
    }

    private static List<Long> initializeFishPerTimer(List<Integer> data) {
        List<Long> fishPerTimer = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            fishPerTimer.add(0L);
        }

        for (Integer timer : data) {
            long numOfFish = fishPerTimer.get(timer);
            numOfFish++;
            fishPerTimer.set(timer, numOfFish);
        }

        return fishPerTimer;
    }

    private static long computeTotalNumberOfFish(List<Long> fishPerTimer) {
        return fishPerTimer.stream().reduce(0L, Long::sum);
    }

    private static List<Integer> readFile(String fileName) throws IOException {
        Path path = Paths.get(fileName);

        Stream<String> lines = Files.lines(path);
        List<String> result = lines.collect(Collectors.toList());
        lines.close();

        String[] nums = result.get(0).split(",");

        return Arrays.stream(nums).map(Integer::parseInt).collect(Collectors.toList());
    }
}
