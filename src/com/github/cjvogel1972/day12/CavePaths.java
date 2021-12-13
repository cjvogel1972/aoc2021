package com.github.cjvogel1972.day12;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CavePaths {
    private final Cave start;
    private final Cave end;
    private final Set<List<Cave>> paths;
    private final CaveTracker tracker;

    public CavePaths(Cave start, Cave end, CaveTracker tracker) {
        this.start = start;
        this.end = end;
        paths = new HashSet<>();
        this.tracker = tracker;
    }

    public void traverseCaves() {
        var path = new ArrayDeque<Cave>();
        path.add(start);
        traverseAllPaths(path, start);
    }

    private void traverseAllPaths(Deque<Cave> path, Cave currentCave) {
        if (currentCave == end) {
            var pathCopy = new ArrayList<>(path);
            paths.add(pathCopy);
            return;
        }

        tracker.visit(currentCave);
        for (var cave : currentCave.getConnectedCaves()) {
            if (tracker.canVisit(cave)) {
                path.addLast(cave);
                traverseAllPaths(path, cave);
                path.removeLast();
            }
        }
        tracker.leave(currentCave);
    }

    public Set<List<Cave>> getPaths() {
        return paths;
    }
}
