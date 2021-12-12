package com.github.cjvogel1972.day10;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Day10 {

    public static void main(String[] args) throws IOException {
        var data = readFile("input-files/day10.txt");

        part1(data);
        part2(data);
    }

    private static List<String> readFile(String fileName) throws IOException {
        var path = Paths.get(fileName);

        var lines = Files.lines(path);
        var result = lines.toList();
        lines.close();

        return result;
    }

    private static void part1(List<String> data) {
        var score = 0;

        for (var line : data) {
            score += computeIllegalLine(line);
        }

        System.out.println("Final score = " + score);
    }

    private static void part2(List<String> data) {
        var lineScores = new ArrayList<Long>();

        for (var line : data) {
            if (computeIllegalLine(line) == 0) {
                var completeLine = completeLine(line);
                lineScores.add(computeCompletedLine(completeLine));
            }
        }

        Collections.sort(lineScores);
        var middle = (lineScores.size() / 2);

        System.out.println("Middle score = " + lineScores.get(middle));
    }

    private static int computeIllegalLine(String line) {
        var score = 0;

        var chars = line.toCharArray();
        var chunks = new ArrayDeque<Character>();

        for (var aChar : chars) {
            var illegal = false;
            switch (aChar) {
                case '(', '[', '{', '<':
                    chunks.push(aChar);
                    break;
                case ')':
                    if (topOfStackEquals(chunks, '(')) {
                        chunks.pop();
                    } else {
                        illegal = true;
                        score = 3;
                    }
                    break;
                case ']':
                    if (topOfStackEquals(chunks, '[')) {
                        chunks.pop();
                    } else {
                        illegal = true;
                        score = 57;
                    }
                    break;
                case '}':
                    if (topOfStackEquals(chunks, '{')) {
                        chunks.pop();
                    } else {
                        illegal = true;
                        score = 1197;
                    }
                    break;
                case '>':
                    if (topOfStackEquals(chunks, '<')) {
                        chunks.pop();
                    } else {
                        illegal = true;
                        score = 25137;
                    }
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + aChar);
            }

            if (illegal) {
                break;
            }
        }

        return score;
    }

    private static String completeLine(String line) {
        var chars = line.toCharArray();
        var chunks = new ArrayDeque<Character>();

        for (var aChar : chars) {
            var incomplete = false;

            switch (aChar) {
                case '(', '[', '{', '<':
                    chunks.push(aChar);
                    break;
                case ')':
                    if (topOfStackEquals(chunks, '(')) {
                        chunks.pop();
                    } else {
                        incomplete = true;
                    }
                    break;
                case ']':
                    if (topOfStackEquals(chunks, '[')) {
                        chunks.pop();
                    } else {
                        incomplete = true;
                    }
                    break;
                case '}':
                    if (topOfStackEquals(chunks, '{')) {
                        chunks.pop();
                    } else {
                        incomplete = true;
                    }
                    break;
                case '>':
                    if (topOfStackEquals(chunks, '<')) {
                        chunks.pop();
                    } else {
                        incomplete = true;
                    }
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + aChar);
            }

            if (incomplete) {
                break;
            }
        }

        var result = new StringBuilder();
        while (!chunks.isEmpty()) {
            var open = chunks.pop();
            switch (open) {
                case '(' -> result.append(')');
                case '[' -> result.append(']');
                case '{' -> result.append('}');
                case '<' -> result.append('>');
                default -> throw new IllegalStateException("Unexpected value: " + open);
            }
        }

        return result.toString();
    }

    private static boolean topOfStackEquals(ArrayDeque<Character> chunks, char aChar) {
        return !chunks.isEmpty() && chunks.peek() == aChar;
    }

    private static long computeCompletedLine(String completedLine) {
        var score = 0L;

        var chars = completedLine.toCharArray();
        for (var aChar : chars) {
            score *= 5;
            score += switch (aChar) {
                case ')' -> 1;
                case ']' -> 2;
                case '}' -> 3;
                case '>' -> 4;
                default -> throw new IllegalStateException("Unexpected value: " + aChar);
            };
        }

        return score;
    }
}
