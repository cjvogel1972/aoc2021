package com.github.cjvogel1972.day14;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.github.cjvogel1972.util.Utilities.readFileLines;

public class Day14 {

    public static void main(String[] args) throws IOException {
        List<String> data = readFileLines("input-files/day14.txt");

        var polymerTemplate = data.get(0);
        var rules = readPolymerInsertionRules(data.subList(2, data.size()));
        part1(polymerTemplate, rules);
        part2(polymerTemplate, rules);
    }

    private static Map<String, String> readPolymerInsertionRules(List<String> lines) {
        var rules = new HashMap<String, String>();
        for (String line : lines) {
            var s = line.split(" -> ");
            rules.put(s[0], s[1]);
        }

        return rules;
    }

    private static void part1(String polymerTemplate, Map<String, String> rules) {
        var currentPolymer = polymerTemplate;
        for (var step = 0; step < 10; step++) {
            var newPolymer = new StringBuilder();
            newPolymer.append(currentPolymer.charAt(0));
            for (int i = 0; i < currentPolymer.length() - 1; i++) {
                var nextTwo = currentPolymer.substring(i, i + 2);
                var insert = rules.get(nextTwo);
                newPolymer.append(insert);
                newPolymer.append(nextTwo.charAt(1));
            }
            currentPolymer = newPolymer.toString();
        }

        Map<String, Long> letterCounts = currentPolymer.codePoints()
                .mapToObj(Character::toString)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        var max = letterCounts.values().stream().mapToLong(l -> l).max().getAsLong();
        var min = letterCounts.values().stream().mapToLong(l -> l).min().getAsLong();
        System.out.println((max - min));
    }

    private static void part2(String polymerTemplate, Map<String, String> rules) {
        var pairCounts = new HashMap<String, Long>();
        for (var i = 0; i < polymerTemplate.length() - 1; i++) {
            var pair = polymerTemplate.substring(i, i + 2);
            pairCounts.put(pair, 1L);
        }

        for (var i = 0; i < 40; i++) {
            var newPairCounts = new HashMap<String, Long>();
            for (var pair : pairCounts.keySet()) {
                var c = pairCounts.get(pair);
                var insert = rules.get(pair);
                var newPair = new StringBuilder();
                newPair.append(pair.charAt(0));
                newPair.append(insert.charAt(0));
                var newCount = newPairCounts.getOrDefault(newPair.toString(), 0L) +c;
                newPairCounts.put(newPair.toString(), newCount);
                newPair = new StringBuilder();
                newPair.append(insert.charAt(0));
                newPair.append(pair.charAt(1));
                newCount = newPairCounts.getOrDefault(newPair.toString(), 0L) + c;
                newPairCounts.put(newPair.toString(), newCount);
            }
            pairCounts = newPairCounts;
        }

        var letterCounts = new HashMap<Character, Long>();
        for (var pair : pairCounts.keySet()) {
            var aChar = pair.charAt(0);
            var count = letterCounts.getOrDefault(aChar, 0L);
            count += pairCounts.get(pair);
            letterCounts.put(aChar, count);
        }
        char lastChar = polymerTemplate.charAt(polymerTemplate.length() - 1);
        long count = letterCounts.getOrDefault(lastChar, 0L);
        count++;
        letterCounts.put(lastChar, count);
        var max = letterCounts.values().stream().mapToLong(l -> l).max().getAsLong();
        var min = letterCounts.values().stream().mapToLong(l -> l).min().getAsLong();
        System.out.println((max - min));
    }
}
