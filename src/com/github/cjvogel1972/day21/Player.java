package com.github.cjvogel1972.day21;

public class Player {
    private final int playerNumber;
    private int position;
    private int score;

    public Player(final int playerNumber, final int initialPosition) {
        this.playerNumber = playerNumber;
        position = initialPosition;
        score = 0;
    }

    public void move(int rolls) {
        position += rolls;
        position %= 10;
        if (position == 0) {
            position = 10;
        }

        score += position;
    }

    public int getScore() {
        return score;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    public Player copy() {
        Player result = new Player(playerNumber, position);
        result.score = score;

        return result;
    }

    @Override
    public String toString() {
        return "Player{" +
                "playerNumber=" + playerNumber +
                ", position=" + position +
                ", score=" + score +
                '}';
    }
}
