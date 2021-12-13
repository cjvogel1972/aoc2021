package com.github.cjvogel1972.day1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SonarTest {

    private Sonar sonar;

    private List<Integer> depths;

    @BeforeEach
    void setup() {
        depths = new ArrayList<>();
        depths.add(199);
        depths.add(200);
        depths.add(208);
        depths.add(210);
        depths.add(200);
        depths.add(207);
        depths.add(240);
        depths.add(269);
        depths.add(260);
        depths.add(263);

        sonar = new Sonar(depths);
    }

    @Test
    void calculateNumberOfIncrements() {
        assertEquals(7, sonar.calculateNumberOfIncrements());
    }
}