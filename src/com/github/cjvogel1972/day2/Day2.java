package com.github.cjvogel1972.day2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day2 {

    public static void main(String[] args) throws IOException {
        List<SubCommand> commands = readSubCommandsFile("input-files/day2.txt");

        System.out.println("Simple Sub:");
        moveSubmarine(new Submarine(), commands);

        System.out.println();

        System.out.println("Complicated Sub:");
        moveSubmarine(new ComplicatedSubmarine(), commands);
    }

    private static List<SubCommand> readSubCommandsFile(String fileName) throws IOException {
        Path path = Paths.get(fileName);

        Stream<String> lines = Files.lines(path);
        List<SubCommand> commands = lines
                .map(SubCommand::new)
                .collect(Collectors.toList());
        lines.close();

        return commands;
    }

    private static void moveSubmarine(Submarine sub, List<SubCommand> commands) {
        sub.move(commands);

        System.out.println("Horizontal = " + sub.getHorizontalPosition() + " depth = " + sub.getDepth());
        System.out.println("Multiplied = " + (sub.getHorizontalPosition() * sub.getDepth()));

    }
}
