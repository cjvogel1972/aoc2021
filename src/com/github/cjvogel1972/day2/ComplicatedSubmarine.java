package com.github.cjvogel1972.day2;

public class ComplicatedSubmarine extends Submarine {
    private int aim;

    public void move(SubCommand command) {
        switch (command.getDirection()) {
            case "forward" -> {
                horizontalPosition += command.getUnits();
                depth += (command.getUnits() * aim);
            }
            case "down" -> aim += command.getUnits();
            case "up" -> aim -= command.getUnits();
        }
    }
}