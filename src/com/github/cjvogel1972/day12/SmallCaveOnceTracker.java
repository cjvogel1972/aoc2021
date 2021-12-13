package com.github.cjvogel1972.day12;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SmallCaveOnceTracker implements CaveTracker {
    private final Map<Cave, Boolean> visited;

    public SmallCaveOnceTracker(List<Cave> caves) {
        visited = new HashMap<>();

        for (var cave : caves) {
            visited.put(cave, false);
        }
    }

    @Override
    public void visit(Cave cave) {
        visited.put(cave, true);
    }

    @Override
    public void leave(Cave cave) {
        visited.put(cave, false);
    }

    @Override
    public boolean canVisit(Cave cave) {
        return cave.isBigCave() || !visited.get(cave);
    }
}
