package com.github.cjvogel1972.day2;

import java.util.List;

public class Submarine {
    protected int horizontalPosition;
    protected int depth;

    public void move(List<SubCommand> commands) {
        for (SubCommand command : commands) {
            move(command);
        }
    }

    public void move(SubCommand command) {
        switch (command.getDirection()) {
            case "forward" -> horizontalPosition += command.getUnits();
            case "down" -> depth += command.getUnits();
            case "up" -> depth -= command.getUnits();
        }
    }

    public int getHorizontalPosition() {
        return horizontalPosition;
    }

    public int getDepth() {
        return depth;
    }
}
