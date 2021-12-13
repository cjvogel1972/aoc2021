package com.github.cjvogel1972.day13;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TransparentPaper {
    private List<List<Boolean>> dots;

    public TransparentPaper(List<Coordinate> coordinates) {
        int maxX = coordinates.stream().mapToInt(coordinate -> coordinate.getX()).max().getAsInt();
        int maxY = coordinates.stream().mapToInt(coordinate -> coordinate.getY()).max().getAsInt();

        dots = new ArrayList<>();
        for (var i = 0; i <= maxY; i++) {
            var row = new ArrayList<Boolean>();
            for (var j = 0; j <= maxX; j++) {
                row.add(false);
            }
            dots.add(row);
        }

        for (Coordinate coordinate : coordinates) {
            dots.get(coordinate.getY()).set(coordinate.getX(), true);
        }
    }

    public void fold(FoldCommand command) {
        if (command.direction().equals("y")) {
            foldY(command.line());
        }
        else {
            foldX(command.line());
        }
    }

    private void foldX(int line) {
        for (var i = 1; i < (dots.get(0).size() - line); i++) {
            for (List<Boolean> row : dots) {
                boolean left = row.get(line - i);
                boolean right = row.get(line + i);
                row.set(line - i, (left | right));
            }
        }

        for (var i = (dots.get(0).size() - 1); i > (line - 1); i--) {
            for (List<Boolean> row : dots) {
                row.remove(i);
            }
        }
    }

    private void foldY(int line) {
        for (var i = 1; i < (dots.size() - line); i++) {
            List<Boolean> lowerRow = dots.get(line + i);
            List<Boolean> upperRow = dots.get(line - i);
            for (var j = 0; j < lowerRow.size(); j++) {
                upperRow.set(j, lowerRow.get(j) | upperRow.get(j));
            }
        }

        for (var i = (dots.size() - 1); i > (line - 1); i--) {
//            System.out.println(i);
            dots.remove(i);
        }
    }

    public int dotsShowing() {
        int sum = 0;

        for (List<Boolean> row : dots) {
            sum += row.stream().filter(Boolean.TRUE::equals).count();
        }

        return sum;
    }

    @Override
    public String toString() {
        var result = new StringBuilder();
        for (List<Boolean> row : dots) {
            String rowValue = row.stream().map(value -> value ? "#" : ".").collect(Collectors.joining());
            result.append(rowValue);
            result.append("\n");
        }

        return result.toString();
    }
}
