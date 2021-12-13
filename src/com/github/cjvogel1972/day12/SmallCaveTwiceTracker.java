package com.github.cjvogel1972.day12;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SmallCaveTwiceTracker implements CaveTracker {
    private final Map<Cave, Integer> visited;
    private final Cave smallCave;

    public SmallCaveTwiceTracker(List<Cave> caves, Cave smallCave) {
        this.smallCave = smallCave;
        visited = new HashMap<>();

        for (var cave : caves) {
            visited.put(cave, 0);
        }
    }

    @Override
    public void visit(Cave cave) {
        visited.compute(cave, (key, value) -> ++value);
    }

    @Override
    public void leave(Cave cave) {
        visited.compute(cave, (key, value) -> --value);
    }

    @Override
    public boolean canVisit(Cave cave) {
        return cave.isBigCave() || (cave == smallCave && visited.get(cave) < 2) || (visited.get(cave) < 1);
    }
}
