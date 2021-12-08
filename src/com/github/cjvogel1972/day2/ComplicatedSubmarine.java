package com.github.cjvogel1972.day2;

public class ComplicatedSubmarine extends Submarine {
    private int aim;

    public void move(SubCommand command) {
        if (command.getDirection().equals("forward")) {
            horizontalPosition += command.getUnits();
            depth += (command.getUnits() * aim);
        }
        else if (command.getDirection().equals("down")) {
            aim += command.getUnits();
        }
        else if (command.getDirection().equals("up")) {
            aim -= command.getUnits();
        }
    }
}