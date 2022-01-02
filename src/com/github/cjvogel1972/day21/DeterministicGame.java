package com.github.cjvogel1972.day21;

public class DeterministicGame extends Game {
    private final Die die;

    public DeterministicGame(Player player1, Player player2, Die die, int winningScore) {
        super(player1, player2, winningScore);
        this.die = die;
    }

    @Override
    public void rollAndMove() {
        var rolls = die.roll() + die.roll() + die.roll();
        numberOfRolls += 3;
        currentPlayer.move(rolls);
    }
}
