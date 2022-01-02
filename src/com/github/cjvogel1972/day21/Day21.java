package com.github.cjvogel1972.day21;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import static com.github.cjvogel1972.util.Utilities.readFileLines;

public class Day21 {

    public static void main(String[] args) throws IOException {
        var data = readFileLines("input-files/day21.txt");
        var line1 = data.get(0);
        var player1 = new Player(1, Integer.parseInt(line1.substring(line1.indexOf(": ") + 2)));
        var line2 = data.get(1);
        var player2 = new Player(2, Integer.parseInt(line2.substring(line2.indexOf(": ") + 2)));

        part1(player1.copy(), player2.copy());
        part2(player1.copy(), player2.copy());
    }

    private static void part1(Player player1, Player player2) {
        var die = new Die(100);
        var game = new DeterministicGame(player1, player2, die, 1000);

        while (!game.isGameOver()) {
            game.turn();
        }

        printLosingScore(game.getLosingPlayer(), game.getNumberOfRolls());
    }

    private static void printLosingScore(Player player, int numberRolls) {
        System.out.println(
                "Player " + player.getPlayerNumber() + " score = " + player.getScore() + " rolls = " + numberRolls + " product = " + (player.getScore() * numberRolls));
    }

    private static void part2(Player player1, Player player2) {
        Map<Integer, Long> wins = new HashMap<>();
        wins.put(1, 0L);
        wins.put(2, 0L);

        Queue<DiracGame> gameQueue = new LinkedList<>();
        gameQueue.add(new DiracGame(player1, player2, 21, 1, gameQueue));

        while (!gameQueue.isEmpty()) {
            DiracGame currentGame = gameQueue.remove();
            if (currentGame.isGameOver()) {
                Player winner = currentGame.getWinningPlayer();
                wins.compute(winner.getPlayerNumber(), (k,v) -> v + currentGame.getInstances());
//                System.out.println(gameQueue.size() + " " + wins);
            }
            else {
                currentGame.turn();
            }
        }

        var maxWins = wins.values().stream().mapToLong(w -> w).max().getAsLong();
        System.out.println("Max wins = " + maxWins);
    }
}
