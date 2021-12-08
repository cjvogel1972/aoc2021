package com.github.cjvogel1972.day8;

import java.util.List;
import java.util.stream.Collectors;

public class Wiring {
    private final String litWires;
    private final int numberOfLitWires;
    private Integer number;

    public Wiring(String wires) {
        this.litWires = wires;
        numberOfLitWires = wires.length();
    }

    public boolean hasNumber() {
        return number != null;
    }

    public String getLitWires() {
        return litWires;
    }

    public int getNumberOfLitWires() {
        return numberOfLitWires;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public boolean isEqual(String litWires) {
        List<Character> current = sortWires(this.litWires);
        List<Character> lit = sortWires(litWires);

        return current.equals(lit);
    }

    public List<Character> getSortedLitWires() {
        return sortWires(this.litWires);
    }

    private List<Character> sortWires(String config) {
        return config.chars()
                .mapToObj(c -> (char) c)
                .sorted()
                .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return "Wiring{" +
                "wires='" + litWires + '\'' +
                ", number=" + number +
                '}';
    }
}
