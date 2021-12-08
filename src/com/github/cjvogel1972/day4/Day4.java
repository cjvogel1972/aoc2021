package com.github.cjvogel1972.day4;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day4 {

    public static void main(String[] args) throws IOException {
        part1();
        part2();
    }

    private static void part1() throws IOException {
        List<String> lines = readFile("input-files/day4.txt");
        List<Integer> numbers = parseNumbers(lines.get(0));
        List<BingoCard> cards = parseCards(lines);

        int lastNumber = -1;
        int score = 0;
        outerloop:
        for (Integer number : numbers) {
            for (BingoCard card : cards) {
                card.checkNumber(number);
                if (card.isWinner()) {
                    lastNumber = number;
                    score = card.computeScore();
                    break outerloop;
                }
            }
        }

        System.out.println("Last number = " + lastNumber + " score = " + score);
        System.out.println("Final score = " + (lastNumber * score));
    }
    private static void part2() throws IOException {
        List<String> lines = readFile("input-files/day4.txt");
        List<Integer> numbers = parseNumbers(lines.get(0));
        List<BingoCard> cards = parseCards(lines);

        int lastNumber = -1;
        int score = 0;
        outerloop:
        for (Integer number : numbers) {
            Iterator<BingoCard> cardIterator = cards.iterator();
            while (cardIterator.hasNext()) {
                BingoCard card = cardIterator.next();
                card.checkNumber(number);
                if (card.isWinner()) {
                    if (cards.size() == 1) {
                        lastNumber = number;
                        score = card.computeScore();
                        break outerloop;
                    }
                    cardIterator.remove();
                }
            }
        }

        System.out.println("Last number = " + lastNumber + " score = " + score);
        System.out.println("Final score = " + (lastNumber * score));
    }

    private static List<String> readFile(String fileName) throws IOException {
        Path path = Paths.get(fileName);

        Stream<String> lines = Files.lines(path);
        List<String> result = lines.filter(line -> !line.trim().isEmpty()).collect(Collectors.toList());
        lines.close();

        return result;
    }

    private static List<Integer> parseNumbers(String line) {
        String[] nums = line.split(",");

        return Arrays.stream(nums).map(Integer::parseInt).collect(Collectors.toList());
    }

    private static List<BingoCard> parseCards(List<String> lines) {
        List<BingoCard> cards = new ArrayList<>();

        for (int i = 1; i < lines.size(); i = i + 5) {
            int[][] squares = new int[5][5];
            for (int j = 0; j < 5; j++) {
                int[] lineSquares = parseLine(lines.get(i + j).trim());
                squares[j] = lineSquares;
            }

            BingoCard card = new BingoCard(squares);
            cards.add(card);
        }

        return cards;
    }

    private static int[] parseLine(String line) {
        int[] squares = new int[5];

        String[] num = line.split("\\s+");
        for (int i = 0; i < 5; i++) {
            squares[i] = Integer.parseInt(num[i]);
        }

        return squares;
    }
}
