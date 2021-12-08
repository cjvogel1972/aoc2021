package com.github.cjvogel1972.day1;

import java.util.ArrayList;
import java.util.List;

public class SlidingWindowSonar extends Sonar {

    public SlidingWindowSonar(List<Integer> depths) {
        super();
        this.depths = calculateSlidingWindow(depths);
    }

    private List<Integer> calculateSlidingWindow(List<Integer> depths) {
        List<Integer> slidingWindow = new ArrayList<>();

        for (int i = 2; i < depths.size(); i++) {
            slidingWindow.add(depths.get(i - 2) + depths.get(i - 1) + depths.get(i));
        }

        return slidingWindow;
    }
}
