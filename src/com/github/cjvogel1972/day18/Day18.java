package com.github.cjvogel1972.day18;

import java.io.IOException;
import java.util.List;

import static com.github.cjvogel1972.util.Utilities.parseFile;

public class Day18 {

    public static void main(String[] args) throws IOException {
        List<SnailfishPair> data = parseFile("input-files/day18.txt", SnailfishParser::parseLine);

        System.out.println(data.get(0));
        part1(data);
        System.out.println(data.get(0));
        part2(data);
    }

    private static void part1(List<SnailfishPair> data) {
        SnailfishPair result = null;
        for (SnailfishPair pair : data) {
            if (result == null) {
                result = pair;
            }
            else {
                result = (SnailfishPair) result.add(pair);
                result.reduce();
            }
        }

        System.out.println(result);
        System.out.println(result.magnitude());
    }

    private static void part2(List<SnailfishPair> data) throws IOException {
        var maxMagnitude = 0;

        for (int i = 0; i < data.size(); i++) {
            SnailfishPair pair1 = data.get(i);
            System.out.println("pair 1 = " + pair1);
            for (int j = 0; j < data.size(); j++) {
                if (i != j) {
                    var pair2 = data.get(j);
                    System.out.println("pair 2 = " + pair2);
                    var result = pair1.add(pair2);
                    result.reduce();
                    System.out.println(result + " " + pair1 + " " + pair2 + " " + result.magnitude());
                    var magnitude = result.magnitude();
                    if (magnitude > maxMagnitude) {
                        maxMagnitude = magnitude;
                    }
                }
            }
        }

        System.out.println("Max magnitude = " + maxMagnitude);
    }
}
