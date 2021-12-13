package com.github.cjvogel1972.day13;

public record FoldCommand(String direction, int line) {
    @Override
    public String toString() {
        return "FoldCommand{" +
                "direction='" + direction + '\'' +
                ", line=" + line +
                '}';
    }
}
