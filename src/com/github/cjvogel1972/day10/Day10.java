package com.github.cjvogel1972.day10;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day10 {

    public static void main(String[] args) throws IOException {
        List<String> data = readFile("input-files/day10.txt");

        part1(data);
        part2(data);
    }

    private static List<String> readFile(String fileName) throws IOException {
        Path path = Paths.get(fileName);

        Stream<String> lines = Files.lines(path);
        List<String> result = lines.collect(Collectors.toList());
        lines.close();

        return result;
    }

    private static void part1(List<String> data) {
        int score = 0;

        for (String line : data) {
            score += computeIllegalLine(line);
        }

        System.out.println("Final score = " + score);
    }

    private static void part2(List<String> data) {
        List<Long> lineScores = new ArrayList<>();

        for (String line : data) {
            if (computeIllegalLine(line) == 0) {
                String completeLine = completeLine(line);
                System.out.println(completeLine);
                lineScores.add(computeCompletedLine(completeLine));
            }
        }

        Collections.sort(lineScores);
        System.out.println(lineScores);
        System.out.println((lineScores.size() / 2));
        int middle = (lineScores.size() / 2);

        System.out.println("Middle score = " + lineScores.get(middle));
    }

    private static int computeIllegalLine(String line) {
        int score = 0;

        char[] chars = line.toCharArray();
        Stack<Character> chunks = new Stack<>();

        for (int i = 0; i < chars.length; i++) {
            boolean illegal = false;
            switch (chars[i]) {
                case '(':
                case '[':
                case '{':
                case '<':
                    chunks.push(chars[i]);
                    break;
                case ')':
                    if (chunks.peek() == '(') {
                        chunks.pop();
                    } else {
//                        System.out.println("Expected '" + chunks.peek() + "' but got ')'");
                        illegal = true;
                        score = 3;
                    }
                    break;
                case ']':
                    if (chunks.peek() == '[') {
                        chunks.pop();
                    } else {
//                        System.out.println("Expected '" + chunks.peek() + "' but got ']'");
                        illegal = true;
                        score = 57;
                    }
                    break;
                case '}':
                    if (chunks.peek() == '{') {
                        chunks.pop();
                    } else {
//                        System.out.println("Expected '" + chunks.peek() + "' but got '}'");
                        illegal = true;
                        score = 1197;
                    }
                    break;
                case '>':
                    if (chunks.peek() == '<') {
                        chunks.pop();
                    } else {
//                        System.out.println("Expected '" + chunks.peek() + "' but got '>'");
                        illegal = true;
                        score = 25137;
                    }
                    break;
            }

            if (illegal) {
                break;
            }
        }

        return score;
    }

    private static String completeLine(String line) {
        char[] chars = line.toCharArray();
        Stack<Character> chunks = new Stack<>();

        for (int i = 0; i < chars.length; i++) {
            boolean incomplete = false;

            switch (chars[i]) {
                case '(':
                case '[':
                case '{':
                case '<':
                    chunks.push(chars[i]);
                    break;
                case ')':
                    if (chunks.peek() == '(') {
                        chunks.pop();
                    } else {
//                        System.out.println("Expected '" + chunks.peek() + "' but got ')'");
                        incomplete = true;
                    }
                    break;
                case ']':
                    if (chunks.peek() == '[') {
                        chunks.pop();
                    } else {
//                        System.out.println("Expected '" + chunks.peek() + "' but got ']'");
                        incomplete = true;
                    }
                    break;
                case '}':
                    if (chunks.peek() == '{') {
                        chunks.pop();
                    } else {
//                        System.out.println("Expected '" + chunks.peek() + "' but got '}'");
                        incomplete = true;
                    }
                    break;
                case '>':
                    if (chunks.peek() == '<') {
                        chunks.pop();
                    } else {
//                        System.out.println("Expected '" + chunks.peek() + "' but got '>'");
                        incomplete = true;
                    }
                    break;
            }

            if (incomplete) {
                break;
            }
        }

        StringBuilder result = new StringBuilder();
        while (!chunks.isEmpty()) {
            char open = chunks.pop();
            switch (open) {
                case '(':
                    result.append(')');
                    break;
                case '[':
                    result.append(']');
                    break;
                case '{':
                    result.append('}');
                    break;
                case '<':
                    result.append('>');
                    break;
            }
        }

        return result.toString();
    }

    private static long computeCompletedLine(String completedLine) {
        long score = 0L;

        char[] chars = completedLine.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            score *= 5;
            switch (chars[i]) {
                case ')':
                    score += 1;
                    break;
                case ']':
                    score += 2;
                    break;
                case '}':
                    score += 3;
                    break;
                case '>':
                    score += 4;
                    break;
            }
        }

        return score;
    }
}
