package com.github.cjvogel1972.day1;

import java.util.List;

public class Sonar {
    protected List<Integer> depths;

    protected Sonar() {
    }

    public Sonar(List<Integer> depths) {
        this.depths = depths;
    }

    public int calculateNumberOfIncrements() {
        var numberOfIncrements = 0;
        for (var i = 1; i < depths.size(); i++) {
            if (depths.get(i) > depths.get(i - 1)) {
                numberOfIncrements++;
            }
        }

        return numberOfIncrements;
    }

}
