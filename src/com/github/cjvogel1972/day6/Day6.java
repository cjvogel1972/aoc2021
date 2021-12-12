package com.github.cjvogel1972.day6;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.github.cjvogel1972.util.Utilities.readFileLines;

public class Day6 {

    public static void main(String[] args) throws IOException {
        computeLanternfish(80);
        computeLanternfish(256);
    }

    private static void computeLanternfish(int numberOfDays) throws IOException {
        var data = readFile("input-files/day6.txt");

        var fishPerTimer = initializeFishPerTimer(data);

        for (var day = 0; day < numberOfDays; day++) {
            var numberOfFishWithTimer0 = fishPerTimer.get(0);
            for (var i = 1; i < 9; i++) {
                fishPerTimer.set((i - 1), fishPerTimer.get(i));
            }
            fishPerTimer.set(8, numberOfFishWithTimer0);
            fishPerTimer.set(6, (fishPerTimer.get(6) + numberOfFishWithTimer0));
        }

        var totalNumberOfFish = computeTotalNumberOfFish(fishPerTimer);
        System.out.println("After " + numberOfDays + " days, there are " + totalNumberOfFish + " lanternfish");
    }

    private static List<Long> initializeFishPerTimer(List<Integer> data) {
        var fishPerTimer = new ArrayList<Long>();
        for (var i = 0; i < 9; i++) {
            fishPerTimer.add(0L);
        }

        for (var timer : data) {
            var numOfFish = fishPerTimer.get(timer);
            numOfFish++;
            fishPerTimer.set(timer, numOfFish);
        }

        return fishPerTimer;
    }

    private static long computeTotalNumberOfFish(List<Long> fishPerTimer) {
        return fishPerTimer.stream()
                .reduce(0L, Long::sum);
    }

    private static List<Integer> readFile(String fileName) throws IOException {
        var result = readFileLines(fileName);

        var nums = result.get(0)
                .split(",");

        return Arrays.stream(nums)
                .map(Integer::parseInt)
                .toList();
    }
}
