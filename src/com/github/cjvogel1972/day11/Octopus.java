package com.github.cjvogel1972.day11;

import java.util.HashSet;
import java.util.Set;

public class Octopus {
    private int energy;
    private boolean flashed;
    private final Set<Octopus> neighbors;

    public Octopus(int initEnergy) {
        energy = initEnergy;
        flashed = false;
        neighbors = new HashSet<>();
    }

    public void incrementEnergy() {
        if (!flashed) {
            energy++;
            checkIfFlash();
        }
    }

    private void checkIfFlash() {
        if (!flashed && energy > 9) {
            flashed = true;
            for (Octopus neighbor : neighbors) {
                neighbor.incrementEnergy();
            }
        }
    }

    public boolean flashedThisStep() {
        return flashed;
    }

    public void resetFlash() {
        flashed = false;
        energy = 0;
    }

    public void addNeighbor(Octopus neighbor) {
        if (neighbor != this) {
            neighbors.add(neighbor);
        }
    }
}
