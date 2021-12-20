package com.github.cjvogel1972.day17;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.github.cjvogel1972.util.Utilities.readFileLines;

public class Day17 {

    public static void main(String[] args) throws IOException {
        List<String> data = readFileLines("input-files/day17.txt");

        TargetArea targetArea = parseTargetArea(data.get(0));

        part1(targetArea);
        System.out.println();
        part2(targetArea);
    }

    private static TargetArea parseTargetArea(String line) {
        var x = parseXRange(line);
        var y = parseYRange(line);
        return new TargetArea(x[0], x[1], y[0], y[1]);
    }

    private static int[] parseXRange(String line) {
        var equalsIndex = line.indexOf('=');
        var commaIndex = line.indexOf(',');
        var xRangeStr = line.substring(equalsIndex + 1, commaIndex);
        return parseRange(xRangeStr);
    }

    private static int[] parseYRange(String line) {
        var equalsIndex = line.lastIndexOf('=');
        var yRangeStr = line.substring(equalsIndex + 1);
        return parseRange(yRangeStr);
    }

    private static int[] parseRange(String rangeStr) {
        var firstPeriodIndex = rangeStr.indexOf('.');
        var num1 = Integer.parseInt(rangeStr.substring(0, firstPeriodIndex));
        var num2 = Integer.parseInt(rangeStr.substring(firstPeriodIndex + 2));
        var min = Math.min(num1, num2);
        var max = Math.max(num1, num2);
        return new int[]{min, max};
    }

    private static void part1(TargetArea targetArea) {
        var xVelocity = findMinInitialXVelocity(targetArea);
        var yVelocity = Math.abs(targetArea.minY()) - 1;

        System.out.println(xVelocity + ", " + yVelocity);
        System.out.println("Max height = " + sumIntegersRange(1, yVelocity));
    }

    private static void part2(TargetArea targetArea) {
        int count = 0;

        var minXVelocity = findMinInitialXVelocity(targetArea);
        var maxXVelocity = targetArea.maxX();
        var maxYVelocity = Math.abs(targetArea.minY()) - 1;
        var minYVelocity = targetArea.minY();

        var yLandsMap = new HashMap<Integer, Boolean>();
        var yStepsMap = new HashMap<Integer, List<Integer>>();
        for (var x = minXVelocity; x <= maxXVelocity; x++) {
            for (var y = minYVelocity; y <= maxYVelocity; y++) {
                var add = false;
                var lands = yLandsMap.computeIfAbsent(y, k -> yLandsInTargetArea(targetArea, k));
                var steps = yStepsMap.computeIfAbsent(y, k -> stepsForYToLandInTargetArea(targetArea, k));

                if (lands) {
                    for (Integer step : steps) {
                        int xDistance;
                        if (step > x) {
                            xDistance = sumIntegersRange(1, x);
                        } else {
                            xDistance = sumIntegersRange(x - (step - 1), x);
                        }

                        if (targetArea.withinX(xDistance)) {
                            add = true;
                            break;
                        }
                    }
                }

                if (add) {
                    count++;
                }
            }
        }

        System.out.println("Number of initial velocities = " + count++);
    }

    private static int findMinInitialXVelocity(TargetArea targetArea) {
        var initXVelocity = 1;

        var maxDistance = sumIntegersRange(1, initXVelocity);
        while (maxDistance < targetArea.minX()) {
            maxDistance = sumIntegersRange(1, ++initXVelocity);
        }

        if (maxDistance > targetArea.maxX()) {
            initXVelocity = 0;
        }

        return initXVelocity;
    }

    private static boolean yLandsInTargetArea(final TargetArea targetArea, final int initYVelocity) {
        var result = true;

        var velocity = initYVelocity;
        var y = 0;

        while (!targetArea.withinY(y) && y >= targetArea.minY()) {
            y += velocity;
            velocity--;
        }

        if (y < targetArea.minY()) {
            result = false;
        }

        return result;
    }

    private static List<Integer> stepsForYToLandInTargetArea(final TargetArea targetArea, final int initYVelocity) {
        var result = new ArrayList<Integer>();

        var velocity = initYVelocity;
        var y = 0;
        var step = 0;

        while (y >= targetArea.minY()) {
            y += velocity;
            velocity--;
            step++;

            if (targetArea.withinY(y)) {
                result.add(step);
            }
        }

        return result;
    }

    private static int sumIntegersRange(final int min, final int max) {
        return ((min + max) * (max - min + 1)) / 2;
    }
}
