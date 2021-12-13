package com.github.cjvogel1972.day4;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BingoCardTest {

    private BingoCard card;

    private List<Integer> numbers;

    @BeforeEach
    void setup() {
        card = new BingoCard(new int[][]{
                {14, 21, 17, 24, 4},
                {10, 16, 15, 9, 19},
                {18, 8, 23, 26, 20},
                {22, 11, 13, 6, 5},
                {2, 0, 12, 3, 7}});

        numbers = new ArrayList<>();
        numbers.add(7);
        numbers.add(4);
        numbers.add(9);
        numbers.add(5);
        numbers.add(11);
        numbers.add(17);
        numbers.add(23);
        numbers.add(2);
        numbers.add(0);
        numbers.add(14);
        numbers.add(21);
        numbers.add(24);
        numbers.add(10);
        numbers.add(16);
        numbers.add(13);
        numbers.add(6);
        numbers.add(25);
        numbers.add(22);
        numbers.add(18);
        numbers.add(20);
        numbers.add(8);
        numbers.add(19);
        numbers.add(3);
        numbers.add(26);
        numbers.add(1);
    }

    @Test
    void isWinner() {
        int lastNumber = -1;
        for (Integer number: numbers) {
            card.checkNumber(number);
            if (card.isWinner()) {
                lastNumber = number;
                break;
            }
        }

        assertEquals(24, lastNumber);
    }

    @Test
    void computeScore() {
        for (Integer number: numbers) {
            card.checkNumber(number);
            if (card.isWinner()) {
                break;
            }
        }

        assertEquals(188, card.computeScore());
    }
}