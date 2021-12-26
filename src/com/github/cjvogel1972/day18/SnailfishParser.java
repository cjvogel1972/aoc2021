package com.github.cjvogel1972.day18;

public class SnailfishParser {

    private SnailfishParser() {}

    public static SnailfishPair parseLine(String line) {
        SnailfishPair result;
        SnailfishElement left;
        SnailfishElement right;

        if (line.length() == 5) {
            result = parseSimplePair(line);
        }
        else {
            var startRightIndex = -1;
            if (line.charAt(0) == '[') {
                int index = calculatePairClosingIndex(line, 1);
                if (index == 5) {
                    left = parseLine(line.substring(0, index));
                } else {
                    left = parseLine(line.substring(1, index - 1));
                }
                if (index < line.length() && line.charAt(index) == ',') {
                    startRightIndex = index + 1;
                }
            } else {
                left = new SnailfishNumber(line.charAt(0) - '0');
                startRightIndex = 2;
            }

            if (startRightIndex != -1) {
                if (line.charAt(startRightIndex) == '[') {
                    var index = calculatePairClosingIndex(line, startRightIndex + 1);
                    if (index - startRightIndex == 5) {
                        right = parseLine(line.substring(startRightIndex, index));
                    } else {
                        right = parseLine(line.substring(startRightIndex + 1, index - 1));
                    }
                } else {
                    right = new SnailfishNumber(line.charAt(startRightIndex) - '0');
                }

                result = new SnailfishPair(left, right);
            }
            else {
                result = (SnailfishPair) left;
            }
        }

        return result;
    }

    private static int calculatePairClosingIndex(String line, int startIndex) {
        var open = 1;
        var index = startIndex;
        while (open > 0) {
            if (line.charAt(index) == '[') {
                open++;
            } else if (line.charAt(index) == ']') {
                open--;
            }
            index++;
        }
        return index;
    }

    public static SnailfishPair parseSimplePair(String pair) {
        var left = pair.charAt(1) - '0';
        var right = pair.charAt(3) - '0';

        return new SnailfishPair(left, right);
    }
}
