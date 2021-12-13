package com.github.cjvogel1972.day12;

public interface CaveTracker {

    void visit(Cave cave);

    void leave(Cave cave);

    boolean canVisit(Cave cave);
}
