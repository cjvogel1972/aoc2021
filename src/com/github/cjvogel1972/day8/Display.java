package com.github.cjvogel1972.day8;

import java.util.List;

public class Display {
    private List<String> displayWiresLit;
    private List<Wiring> wiringConfiguration;

    public Display(List<String> displayWiresLit, List<Wiring> wiringConfiguration) {
        this.displayWiresLit = displayWiresLit;
        this.wiringConfiguration = wiringConfiguration;
    }

    public int computeNumber() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < displayWiresLit.size(); i++) {
            int index = i;
            Wiring wiring = wiringConfiguration.stream()
                    .filter(config -> config.isEqual(displayWiresLit.get(index)))
                    .findFirst()
                    .get();
            sb.append(wiring.getNumber());
        }

        return Integer.parseInt(sb.toString());
    }
}
