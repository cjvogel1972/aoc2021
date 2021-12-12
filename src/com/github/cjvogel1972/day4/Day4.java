package com.github.cjvogel1972.day4;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day4 {

    public static void main(String[] args) throws IOException {
        var lines = readFile("input-files/day4.txt");
        var numbers = parseNumbers(lines.get(0));

        part1(lines, numbers);
        part2(lines, numbers);
    }

    private static List<String> readFile(String fileName) throws IOException {
        var path = Paths.get(fileName);

        var lines = Files.lines(path);
        var result = lines.filter(line -> !line.trim()
                        .isEmpty())
                .toList();
        lines.close();

        return result;
    }

    private static List<Integer> parseNumbers(String line) {
        var nums = line.split(",");

        return Arrays.stream(nums)
                .map(Integer::parseInt)
                .toList();
    }

    private static void part1(List<String> lines, List<Integer> numbers) {
        var cards = parseCards(lines);

        var lastNumber = -1;
        var score = 0;
        outerloop:
        for (var number : numbers) {
            for (var card : cards) {
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

    private static void part2(List<String> lines, List<Integer> numbers) {
        var cards = parseCards(lines);

        var lastNumber = -1;
        var score = 0;
        outerloop:
        for (var number : numbers) {
            var cardIterator = cards.iterator();
            while (cardIterator.hasNext()) {
                var card = cardIterator.next();
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

    private static List<BingoCard> parseCards(List<String> lines) {
        var cards = new ArrayList<BingoCard>();

        for (var i = 1; i < lines.size(); i = i + 5) {
            var squares = new int[5][5];
            for (var j = 0; j < 5; j++) {
                var lineSquares = parseLine(lines.get(i + j));
                squares[j] = lineSquares;
            }

            var card = new BingoCard(squares);
            cards.add(card);
        }

        return cards;
    }

    private static int[] parseLine(String line) {
        var squares = new int[5];

        var num = line.trim()
                .split("\\s+");
        for (var i = 0; i < 5; i++) {
            squares[i] = Integer.parseInt(num[i]);
        }

        return squares;
    }
}
