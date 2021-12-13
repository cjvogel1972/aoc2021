package com.github.cjvogel1972.day13;

import com.github.cjvogel1972.util.Coordinate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class TransparentPaper {
    private final List<List<Boolean>> dots;

    public TransparentPaper(List<Coordinate> coordinates) {
        int maxX = coordinates.stream()
                .mapToInt(Coordinate::x)
                .max()
                .getAsInt();
        int maxY = coordinates.stream()
                .mapToInt(Coordinate::y)
                .max()
                .getAsInt();

        dots = new ArrayList<>();
        for (var i = 0; i <= maxY; i++) {
            dots.add(new ArrayList<>(Collections.nCopies((maxX + 1), false)));
        }

        for (Coordinate coordinate : coordinates) {
            dots.get(coordinate.y())
                    .set(coordinate.x(), true);
        }
    }

    public void fold(FoldCommand command) {
        if (command.direction()
                .equals("y")) {
            foldY(command.line());
        } else {
            foldX(command.line());
        }
    }

    private void foldX(int line) {
        for (List<Boolean> row : dots) {
            for (var i = 1; i < (row.size() - line); i++) {
                boolean left = row.get(line - i);
                boolean right = row.get(line + i);
                row.set((line - i), (left || right));
            }
        }

        for (List<Boolean> row : dots) {
            row.subList(line, row.size())
                    .clear();
        }
    }

    private void foldY(int line) {
        for (var i = 1; i < (dots.size() - line); i++) {
            List<Boolean> lowerRow = dots.get(line + i);
            List<Boolean> upperRow = dots.get(line - i);
            for (var j = 0; j < lowerRow.size(); j++) {
                upperRow.set(j, (lowerRow.get(j) || upperRow.get(j)));
            }
        }

        if (dots.size() > line) {
            dots.subList(line, dots.size())
                    .clear();
        }
    }

    public int dotsShowing() {
        int sum = 0;

        for (List<Boolean> row : dots) {
            sum += row.stream()
                    .filter(Boolean.TRUE::equals)
                    .count();
        }

        return sum;
    }

    @Override
    public String toString() {
        var result = new StringBuilder();
        for (List<Boolean> row : dots) {
            String rowValue = row.stream()
                    .map(value -> Boolean.TRUE.equals(value) ? "#" : ".")
                    .collect(Collectors.joining("", "", "\n"));
            result.append(rowValue);
        }

        return result.toString();
    }
}
