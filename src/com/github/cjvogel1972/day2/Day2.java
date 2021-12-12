package com.github.cjvogel1972.day2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Day2 {

    public static void main(String[] args) throws IOException {
        var commands = readFile("input-files/day2.txt");

        part1(commands);
        System.out.println();
        part2(commands);
    }

    private static List<SubCommand> readFile(String fileName) throws IOException {
        var path = Paths.get(fileName);

        var lines = Files.lines(path);
        var commands = lines
                .map(SubCommand::new)
                .toList();
        lines.close();

        return commands;
    }

    private static void part1(List<SubCommand> commands) {
        System.out.println("Simple Sub:");
        moveSubmarine(new Submarine(), commands);
    }

    private static void part2(List<SubCommand> commands) {
        System.out.println("Complicated Sub:");
        moveSubmarine(new ComplicatedSubmarine(), commands);
    }

    private static void moveSubmarine(Submarine sub, List<SubCommand> commands) {
        sub.move(commands);

        System.out.println("Horizontal = " + sub.getHorizontalPosition() + " depth = " + sub.getDepth());
        System.out.println("Multiplied = " + (sub.getHorizontalPosition() * sub.getDepth()));

    }
}
