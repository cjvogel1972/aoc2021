package com.github.cjvogel1972.day8;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.github.cjvogel1972.util.Utilities.readFileLines;

public class Day8 {

    public static void main(String[] args) throws IOException {
        part1();
        part2();
    }

    private static void part1() throws IOException {
        var data = readFileLines("input-files/day8.txt");

        var sum = data.stream()
                .map(line -> countOneFourSevenEights(splitWiringConfigFromNumbers(line)[1]))
                .reduce(0, Integer::sum);

        System.out.println("Number of 1, 4, 7, 8 = " + sum);
    }

    private static void part2() throws IOException {
        var data = readFileLines("input-files/day8.txt");

        var sum = data.stream()
                .map(Day8::computeDisplayNumber)
                .reduce(0L, Long::sum);

        System.out.println("Sum of displays = " + sum);
    }

    private static int countOneFourSevenEights(String wiring) {
        var count = 0;

        var numbers = wiring.trim()
                .split("\\s+");
        for (var number : numbers) {
            var numberOfLitWires = number.length();
            if (numberOfLitWires == 2 || numberOfLitWires == 4 || numberOfLitWires == 3 || numberOfLitWires == 7) {
                count++;
            }
        }

        return count;
    }

    private static long computeDisplayNumber(String line) {
        var data = splitWiringConfigFromNumbers(line);
        var wiringConfigs = solveWiringConfiguration(data[0]);
        var digits = data[1].trim()
                .split("\\s+");
        var displayDigits = Arrays.asList(digits);

        var display = new Display(displayDigits, wiringConfigs);
        return display.computeNumber();
    }

    private static List<Wiring> solveWiringConfiguration(String data) {
        var configs = data.trim()
                .split("\\s+");
        var wiringConfigs = Arrays.stream(configs)
                .map(Wiring::new)
                .toList();

        var numberToWiringMap = new HashMap<Integer, Wiring>();
        solveOneTwoSevenEight(wiringConfigs, numberToWiringMap);
        solveThreeFiveSixNine(wiringConfigs, numberToWiringMap);
        solveZeroTwo(wiringConfigs, numberToWiringMap);

        return wiringConfigs;
    }

    private static void solveOneTwoSevenEight(List<Wiring> wiringConfigs, Map<Integer, Wiring> numberToWiringMap) {
        for (var config : wiringConfigs) {
            if (config.getNumberOfLitWires() == 2) {
                config.setNumber(1);
                numberToWiringMap.put(1, config);
            } else if (config.getNumberOfLitWires() == 4) {
                config.setNumber(4);
                numberToWiringMap.put(4, config);
            } else if (config.getNumberOfLitWires() == 3) {
                config.setNumber(7);
                numberToWiringMap.put(7, config);
            } else if (config.getNumberOfLitWires() == 7) {
                config.setNumber(8);
                numberToWiringMap.put(8, config);
            }
        }
    }

    private static void solveThreeFiveSixNine(List<Wiring> wiringConfigs, Map<Integer, Wiring> numberToWiringMap) {
        while (wiringConfigs.stream()
                .filter(Wiring::hasNumber)
                .count() != 8) {
            for (var config : wiringConfigs) {
                if (config.hasNumber()) {
                    continue;
                }
                var configWires = config.getSortedLitWires();
                if (checkForThree(config, numberToWiringMap, configWires)) {
                    config.setNumber(3);
                    numberToWiringMap.put(3, config);
                } else if (checkForFive(config, numberToWiringMap, configWires)) {
                    config.setNumber(5);
                    numberToWiringMap.put(5, config);
                } else if (checkForNine(config, numberToWiringMap, configWires)) {
                    config.setNumber(9);
                    numberToWiringMap.put(9, config);
                } else if (checkForSix(config, numberToWiringMap, configWires)) {
                    config.setNumber(6);
                    numberToWiringMap.put(6, config);
                }
            }
        }
    }

    private static boolean checkForThree(Wiring configToCheck, Map<Integer, Wiring> numberToWiringMap,
                                         List<Character> configWires) {
        var result = false;

        if (configToCheck.getNumberOfLitWires() == 5) {
            var sevenConfig = numberToWiringMap.get(7);
            var sevenConfigWires = sevenConfig.getSortedLitWires();
            if (configWires.containsAll(sevenConfigWires)) {
                result = true;
            }
        }

        return result;
    }

    private static boolean checkForFive(Wiring config, Map<Integer, Wiring> numberToWiringMap,
                                        List<Character> configWires) {
        var result = false;

        if (config.getNumberOfLitWires() == 5) {
            var nineConfig = numberToWiringMap.get(9);
            if (nineConfig != null) {
                var nineConfigWires = nineConfig.getSortedLitWires();
                var intersection = configWires.stream()
                        .filter(nineConfigWires::contains)
                        .toList();
                if (intersection.size() == nineConfig.getNumberOfLitWires() - 1) {
                    result = true;
                }
            }
        }

        return result;
    }

    private static boolean checkForNine(Wiring config, Map<Integer, Wiring> numberToWiringMap,
                                        List<Character> configWires) {
        var result = false;

        if (config.getNumberOfLitWires() == 6) {
            var fourConfig = numberToWiringMap.get(4);
            var fourConfigWires = fourConfig.getSortedLitWires();
            if (configWires.containsAll(fourConfigWires)) {
                result = true;
            }
        }

        return result;
    }

    private static boolean checkForSix(Wiring config, Map<Integer, Wiring> numberToWiringMap,
                                       List<Character> configWires) {
        var result = false;

        if (config.getNumberOfLitWires() == 6) {
            var oneConfig = numberToWiringMap.get(1);
            var oneConfigWires = oneConfig.getSortedLitWires();
            var intersection = configWires.stream()
                    .filter(oneConfigWires::contains)
                    .toList();
            if (intersection.size() == oneConfig.getNumberOfLitWires() - 1) {
                result = true;
            }
        }

        return result;
    }

    private static void solveZeroTwo(List<Wiring> wiringConfigs, Map<Integer, Wiring> numberToWiringMap) {
        for (var config : wiringConfigs) {
            if (config.hasNumber()) {
                continue;
            }
            if (config.getNumberOfLitWires() == 5) {
                config.setNumber(2);
                numberToWiringMap.put(2, config);
            } else if (config.getNumberOfLitWires() == 6) {
                config.setNumber(0);
                numberToWiringMap.put(0, config);
            }
        }
    }

    private static String[] splitWiringConfigFromNumbers(String line) {
        return line.split("\\|");
    }
}
