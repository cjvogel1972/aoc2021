package com.github.cjvogel1972.day13;

import com.github.cjvogel1972.util.Coordinate;

import java.io.IOException;
import java.util.ArrayList;

import static com.github.cjvogel1972.util.Utilities.readFileLines;

public class Day13 {

    public static void main(String[] args) throws IOException {
        var lines = readFileLines("input-files/day13.txt");

        var coordinates = new ArrayList<Coordinate>();
        var i = 0;
        while (!lines.get(i).isEmpty()) {
            coordinates.add(parseCoordinate(lines.get(i)));
            i++;
        }

        var commands = new ArrayList<FoldCommand>();
        for (int j = i + 1; j < lines.size(); j++) {
            commands.add(parseCommand(lines.get(j)));
        }

        part1(coordinates, commands);
        System.out.println();
        part2(coordinates, commands);
    }

    private static Coordinate parseCoordinate(String coordStr) {
        var s = coordStr.split(",");

        var x = Integer.parseInt(s[0]);
        var y = Integer.parseInt(s[1]);

        return new Coordinate(x, y);
    }

    private static FoldCommand parseCommand(String commandStr) {
        var s = commandStr.split(" ");
        var data = s[2];
        var splitData = data.split("=");
        var direction = splitData[0];
        var line = Integer.parseInt(splitData[1]);
        return new FoldCommand(direction, line);
    }

    private static void part1(ArrayList<Coordinate> coordinates, ArrayList<FoldCommand> commands) {
        TransparentPaper paper = new TransparentPaper(coordinates);
        paper.fold(commands.get(0));

        System.out.println("Dots showing = " + paper.dotsShowing());
    }

    private static void part2(ArrayList<Coordinate> coordinates, ArrayList<FoldCommand> commands) {
        TransparentPaper paper = new TransparentPaper(coordinates);
        for (FoldCommand command : commands) {
            paper.fold(command);
        }

        System.out.println(paper.toString());
    }
}
