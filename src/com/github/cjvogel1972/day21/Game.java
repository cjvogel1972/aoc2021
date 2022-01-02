package com.github.cjvogel1972.day21;

public abstract class Game {
    protected final Player player1;
    protected final Player player2;
    protected final int winningScore;
    protected Player currentPlayer;
    protected Player otherPlayer;
    protected int numberOfRolls;

    protected Game(Player player1, Player player2, int winningScore) {
        this.player1 = player1;
        this.player2 = player2;
        this.winningScore = winningScore;
        currentPlayer = player1;
        otherPlayer = player2;
        numberOfRolls = 0;
    }

    public int getNumberOfRolls() {
        return numberOfRolls;
    }

    public void turn() {
        rollAndMove();
        nextPlayer();
    }

    protected abstract void rollAndMove();

    public boolean isGameOver() {
        return player1.getScore() >= winningScore || player2.getScore() >= winningScore;
    }

    public Player getWinningPlayer() {
        return player1.getScore() > player2.getScore() ? player1 : player2;
    }

    public Player getLosingPlayer() {
        return player1.getScore() > player2.getScore() ? player2 : player1;
    }

    protected void nextPlayer() {
        var tmp = currentPlayer;
        currentPlayer = otherPlayer;
        otherPlayer = tmp;
    }
}
