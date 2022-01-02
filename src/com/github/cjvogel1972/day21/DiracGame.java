package com.github.cjvogel1972.day21;

import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

public class DiracGame extends Game {

    private static final Map<Integer, Integer> DIRAC_DICE_ROLLS = new HashMap<>();

    static {
        DIRAC_DICE_ROLLS.put(3, 1);
        DIRAC_DICE_ROLLS.put(4, 3);
        DIRAC_DICE_ROLLS.put(5, 6);
        DIRAC_DICE_ROLLS.put(6, 7);
        DIRAC_DICE_ROLLS.put(7, 6);
        DIRAC_DICE_ROLLS.put(8, 3);
        DIRAC_DICE_ROLLS.put(9, 1);
    }

    private final long instances;
    private final Queue<DiracGame> games;

    public DiracGame(Player player1, Player player2, int winningScore, long instances, Queue<DiracGame> games) {
        super(player1, player2, winningScore);
        this.instances = instances;
        this.games = games;
    }

    @Override
    protected void rollAndMove() {
        for (Map.Entry<Integer, Integer> entry : DIRAC_DICE_ROLLS.entrySet()) {
            var newGame = new DiracGame(player1.copy(), player2.copy(), winningScore, instances * entry.getValue(), games);
            newGame.setCurrentPlayer(currentPlayer.getPlayerNumber());
            newGame.currentPlayer.move(entry.getKey());
            newGame.setCurrentPlayer(otherPlayer.getPlayerNumber());
            games.add(newGame);
        }
    }

    public long getInstances() {
        return instances;
    }

    public void setCurrentPlayer(int playerNumber) {
        if (playerNumber == 1) {
            currentPlayer = player1;
            otherPlayer = player2;
        }
        else {
            currentPlayer = player2;
            otherPlayer = player1;
        }
    }
    @Override
    public String toString() {
        return "DiracGame{" +
                "instances=" + instances +
                ", player1=" + player1 +
                ", player2=" + player2 +
                ", currentPlayer=" + currentPlayer +
                ", otherPlayer=" + otherPlayer +
                '}';
    }
}
