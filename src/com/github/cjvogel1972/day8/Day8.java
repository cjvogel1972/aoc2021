package com.github.cjvogel1972.day8;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day8 {

    public static void main(String[] args) throws IOException {
        part1();
        part2();
    }

    private static void part1() throws IOException {
        List<String> data = readFile("input-files/day8.txt");

        Integer sum = data.stream()
                .map(line -> countOneFourSevenEights(splitWiringConfigFromNumbers(line)[1]))
                .reduce(0, Integer::sum);

        System.out.println("Number of 1, 4, 7, 8 = " + sum);
    }

    private static void part2() throws IOException {
        List<String> data = readFile("input-files/day8.txt");

        Long sum = data.stream()
                .map(Day8::computeDisplayNumber)
                .reduce(0L, Long::sum);

        System.out.println("Sum of displays = " + sum);
    }

    private static int countOneFourSevenEights(String wiring) {
        int count = 0;

        String[] numbers = wiring.trim()
                .split("\\s+");
        for (String number : numbers) {
            int numberOfLitWires = number.length();
            if (numberOfLitWires == 2 || numberOfLitWires == 4 || numberOfLitWires == 3 || numberOfLitWires == 7) {
                count++;
            }
        }

        return count;
    }

    private static long computeDisplayNumber(String line) {
        String[] data = splitWiringConfigFromNumbers(line);
        List<Wiring> wiringConfigs = solveWiringConfiguration(data[0]);
        String[] digits = data[1].trim()
                .split("\\s+");
        List<String> displayDigits = Arrays.asList(digits);

        Display display = new Display(displayDigits, wiringConfigs);
        return display.computeNumber();
    }

    private static List<Wiring> solveWiringConfiguration(String data) {
        String[] configs = data.trim()
                .split("\\s+");
        List<Wiring> wiringConfigs = Arrays.stream(configs)
                .map(config -> new Wiring(config))
                .collect(Collectors.toList());

        Map<Integer, Wiring> numberToWiringMap = new HashMap<>();
        solveOneTwoSevenEight(wiringConfigs, numberToWiringMap);
        solveThreeFiveSixNine(wiringConfigs, numberToWiringMap);
        solveZeroTwo(wiringConfigs, numberToWiringMap);

        return wiringConfigs;
    }

    private static void solveOneTwoSevenEight(List<Wiring> wiringConfigs, Map<Integer, Wiring> numberToWiringMap) {
        for (Wiring config : wiringConfigs) {
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
            for (Wiring config : wiringConfigs) {
                if (config.hasNumber()) {
                    continue;
                }
                List<Character> configWires = config.getSortedLitWires();
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
        boolean result = false;

        if (configToCheck.getNumberOfLitWires() == 5) {
            Wiring sevenConfig = numberToWiringMap.get(7);
            List<Character> sevenConfigWires = sevenConfig.getSortedLitWires();
            if (configWires.containsAll(sevenConfigWires)) {
                result = true;
            }
        }

        return result;
    }

    private static boolean checkForFive(Wiring config, Map<Integer, Wiring> numberToWiringMap,
                                        List<Character> configWires) {
        boolean result = false;

        if (config.getNumberOfLitWires() == 5) {
            Wiring nineConfig = numberToWiringMap.get(9);
            if (nineConfig != null) {
                List<Character> nineConfigWires = nineConfig.getSortedLitWires();
                List<Character> intersection = configWires.stream()
                        .filter(nineConfigWires::contains)
                        .collect(Collectors.toList());
                if (intersection.size() == nineConfig.getNumberOfLitWires() - 1) {
                    result = true;
                }
            }
        }

        return result;
    }

    private static boolean checkForNine(Wiring config, Map<Integer, Wiring> numberToWiringMap,
                                        List<Character> configWires) {
        boolean result = false;

        if (config.getNumberOfLitWires() == 6) {
            Wiring fourConfig = numberToWiringMap.get(4);
            List<Character> fourConfigWires = fourConfig.getSortedLitWires();
            if (configWires.containsAll(fourConfigWires)) {
                result = true;
            }
        }

        return result;
    }

    private static boolean checkForSix(Wiring config, Map<Integer, Wiring> numberToWiringMap,
                                       List<Character> configWires) {
        boolean result = false;

        if (config.getNumberOfLitWires() == 6) {
            Wiring oneConfig = numberToWiringMap.get(1);
            List<Character> oneConfigWires = oneConfig.getSortedLitWires();
            List<Character> intersection = configWires.stream()
                    .filter(oneConfigWires::contains)
                    .collect(Collectors.toList());
            if (intersection.size() == oneConfig.getNumberOfLitWires() - 1) {
                result = true;
            }
        }

        return result;
    }

    private static void solveZeroTwo(List<Wiring> wiringConfigs, Map<Integer, Wiring> numberToWiringMap) {
        for (Wiring config : wiringConfigs) {
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

    private static List<String> readFile(String fileName) throws IOException {
        Path path = Paths.get(fileName);

        Stream<String> lines = Files.lines(path);
        List<String> result = lines.collect(Collectors.toList());
        lines.close();

        return result;
    }
}
