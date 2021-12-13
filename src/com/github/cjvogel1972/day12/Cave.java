package com.github.cjvogel1972.day12;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Cave {
    private String name;
    private List<Cave> connectedCaves;

    public Cave(String name) {
        this.name = name;
        connectedCaves = new ArrayList<>();
    }

    public void addConnectedCave(Cave cave) {
        connectedCaves.add(cave);
    }

    public String getName() {
        return name;
    }

    public boolean isBigCave() {
        return name.toUpperCase().equals(name);
    }

    public boolean isSmallCave() {
        return !isBigCave();
    }

    public List<Cave> getConnectedCaves() {
        return connectedCaves;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cave cave = (Cave) o;
        return name.equals(cave.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return name;
    }
}
