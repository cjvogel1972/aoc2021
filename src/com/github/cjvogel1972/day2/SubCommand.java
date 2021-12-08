package com.github.cjvogel1972.day2;

public class SubCommand {
    private final String direction;
    private final int units;

    public SubCommand(String command) {
        String[] s = command.split(" ");

        this.direction = s[0];
        this.units = Integer.parseInt(s[1]);
    }

    public SubCommand(String direction, int units) {
        this.direction = direction;
        this.units = units;
    }

    public String getDirection() {
        return direction;
    }

    public int getUnits() {
        return units;
    }
}
