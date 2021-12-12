package com.github.cjvogel1972.day8;

import java.util.List;

public class Display {
    private final List<String> displayWiresLit;
    private final List<Wiring> wiringConfiguration;

    public Display(List<String> displayWiresLit, List<Wiring> wiringConfiguration) {
        this.displayWiresLit = displayWiresLit;
        this.wiringConfiguration = wiringConfiguration;
    }

    public int computeNumber() {
        var sb = new StringBuilder();
        for (var i = 0; i < displayWiresLit.size(); i++) {
            var index = i;
            var wiring = wiringConfiguration.stream()
                    .filter(config -> config.isEqual(displayWiresLit.get(index)))
                    .findFirst()
                    .get();
            sb.append(wiring.getNumber());
        }

        return Integer.parseInt(sb.toString());
    }
}
