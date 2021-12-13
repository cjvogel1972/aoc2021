package com.github.cjvogel1972.day2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ComplicatedSubmarineTest {

    private ComplicatedSubmarine sub;

    private List<SubCommand> commands;

    @BeforeEach
    void setup() {
        sub = new ComplicatedSubmarine();

        commands = new ArrayList<>();
        commands.add(new SubCommand("forward", 5));
        commands.add(new SubCommand("down", 5));
        commands.add(new SubCommand("forward", 8));
        commands.add(new SubCommand("up", 3));
        commands.add(new SubCommand("down", 8));
        commands.add(new SubCommand("forward", 2));
    }

    @Test
    void move() {
        for (SubCommand command : commands) {
            sub.move(command);
        }
        assertEquals(15, sub.getHorizontalPosition());
        assertEquals(60, sub.getDepth());
    }
}