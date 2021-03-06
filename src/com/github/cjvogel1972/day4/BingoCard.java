package com.github.cjvogel1972.day4;

import java.util.Arrays;

public class BingoCard {
    private final int[][] squares;

    public BingoCard(int[][] squares) {
        this.squares = squares;
    }

    public void checkNumber(int number) {
        for (var i = 0; i < 5; i++) {
            for (var j = 0; j < 5; j++) {
                if (squares[i][j] == number) {
                    squares[i][j] = -1;
                }
            }
        }
    }

    public boolean isWinner() {
        var win = false;

        for (var i = 0; i < 5; i++) {
            win |= checkRowForWin(i);
            win |= checkColumnForWin(i);
        }

        return win;
    }

    private boolean checkRowForWin(int row) {
        var win = true;

        for (var i = 0; i < 5; i++) {
            win &= (squares[row][i] == -1);
        }

        return win;
    }

    private boolean checkColumnForWin(int column) {
        var win = true;

        for (var i = 0; i < 5; i++) {
            win &= (squares[i][column] == -1);
        }

        return win;
    }

    public int computeScore() {
        int score = 0;

        for (var i = 0; i < 5; i++) {
            for (var j = 0; j < 5; j++) {
                if (squares[i][j] != -1) {
                    score += squares[i][j];
                }
            }
        }

        return score;
    }

    @Override
    public String toString() {
        return "BingoCard{" +
                "squares=[" + Arrays.toString(squares[0]) + ",\n" +
                Arrays.toString(squares[1]) + ",\n" +
                Arrays.toString(squares[2]) + ",\n" +
                Arrays.toString(squares[3]) + ",\n" +
                Arrays.toString(squares[4]) + ",\n]}\n";
    }
}
